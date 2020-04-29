package com.jellyfishmix.wxinterchange.service.impl;

import com.jellyfishmix.wxinterchange.dao.CollectionFileDao;
import com.jellyfishmix.wxinterchange.dao.CollectionInfoDao;
import com.jellyfishmix.wxinterchange.dto.CollectionFileDTO;
import com.jellyfishmix.wxinterchange.entity.CollectionFile;
import com.jellyfishmix.wxinterchange.entity.CollectionInfo;
import com.jellyfishmix.wxinterchange.enums.CollectionEnum;
import com.jellyfishmix.wxinterchange.exception.CollectionException;
import com.jellyfishmix.wxinterchange.service.CollectionService;
import com.jellyfishmix.wxinterchange.service.RedisLockService;
import com.jellyfishmix.wxinterchange.utils.PageCalculatorUtil;
import com.jellyfishmix.wxinterchange.utils.UniqueKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author JellyfishMIX
 * @date 2020/4/29 6:06 下午
 */
@Service("collectionService")
public class CollectionServiceImpl implements CollectionService {
    @Resource
    private CollectionInfoDao collectionInfoDao;
    @Resource
    private CollectionFileDao collectionFileDao;
    @Autowired
    private RedisLockService redisLockService;

    /**
     * 通过uid获取收藏集文件列表
     *
     * @param uid       用户uid
     * @param pageIndex 页码，从1开始
     * @param pageSize  每页行数
     * @return 收藏集文件列表
     */
    @Override
    public List<CollectionFileDTO> queryFileList(String uid, int pageIndex, int pageSize) {
        int rowIndex = PageCalculatorUtil.calculatorRowIndex(pageIndex, pageSize);
        CollectionInfo collectionInfo = collectionInfoDao.queryByUid(uid);
        List<CollectionFileDTO> collectionFileDTOList = collectionFileDao.queryListByCollectionId(collectionInfo.getCollectionId(), rowIndex, pageSize);
        return collectionFileDTOList;
    }

    /**
     * 新建一个收藏集
     *
     * @param collectionInfo 收藏集信息对象
     */
    @Override
    public void createCollection(CollectionInfo collectionInfo) {
        String collectionId = UniqueKeyUtil.getUniqueKey();
        collectionInfo.setCollectionId(collectionId);
        collectionInfoDao.insert(collectionInfo);
    }

    /**
     * 批量添加文件至收藏集
     *
     * @param uid 操作者uid
     * @param collectionFileList 收藏集文件列表
     */
    @Override
    @Transactional(rollbackFor = CollectionException.class)
    public void addedFileList(String uid, List<CollectionFile> collectionFileList) {
        CollectionInfo collectionInfo = collectionInfoDao.queryByUid(uid);
        String collectionId = collectionInfo.getCollectionId();

        // 加分布式锁，避免出现S锁和X锁循环等待死锁
        // 分布式锁过期时间
        int timeout = 20 * 1000;
        long time = System.currentTimeMillis() + timeout;
        // 加锁
        String collectionIdForLock = collectionId.concat("-addedFileList");
        while (!redisLockService.lock(collectionIdForLock, String.valueOf(time))) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // List循环组装
        int countRepeatedNum = 0;
        for (int i = 0; i < collectionFileList.size(); i ++) {
            String fileId = collectionFileList.get(i).getFileId();
            // 防止出现收藏重叠（即收藏已存在）。出现重复的收藏文件，先删除已有的，再添加新选中的
            CollectionFile collectionFileForCheck = collectionFileDao.queryByCollectionIdAndFileId(collectionId, fileId);
            if (collectionFileForCheck != null) {
                collectionFileDao.deleteByCollectionIdAndFileId(collectionId, fileId);
                countRepeatedNum ++;
            }

            CollectionFile collectionFile = new CollectionFile();
            collectionFile.setCollectionId(collectionId);
            collectionFile.setFileId(fileId);
            collectionFileList.set(i, collectionFile);
        }

        collectionFileDao.insertList(collectionFileList);
        // 修改项目组文件计数
        updateCollectionCountProperty(collectionId, CollectionEnum.UPDATE_FILE_COUNT, (collectionFileList.size() - countRepeatedNum));

        // 解锁
        redisLockService.unlock(collectionIdForLock, String.valueOf(time));
    }

    /**
     * 更新收藏集的计数属性
     *
     * @param collectionId   收藏集collectionId
     * @param collectionEnum 操作标志Enum
     * @param countChangeNum 计数更改的数量，有正负
     */
    @Override
    public void updateCollectionCountProperty(String collectionId, CollectionEnum collectionEnum, Integer countChangeNum) {
        CollectionInfo collectionInfoFromQuery = collectionInfoDao.queryByCollectionId(collectionId);
        CollectionInfo collectionInfoForUpdate = new CollectionInfo();
        collectionInfoForUpdate.setCollectionId(collectionId);
        if (collectionEnum.getStateCode().equals(CollectionEnum.UPDATE_FILE_COUNT.getStateCode())) {
            collectionInfoForUpdate.setFileCount(collectionInfoFromQuery.getFileCount());
        }
        collectionInfoDao.update(collectionInfoForUpdate);
    }

    /**
     * 删除文件列表（批量）
     *
     * @param collectionId 收藏集collectionId
     * @param collectionFileList 收藏集文件列表
     */
    @Override
    public void deleteFileList(String collectionId, List<CollectionFile> collectionFileList) {
        // List循环组装
        for (int i = 0; i < collectionFileList.size(); i ++) {
            String fileId = collectionFileList.get(i).getFileId();

            CollectionFile collectionFile = new CollectionFile();
            collectionFile.setCollectionId(collectionId);
            collectionFile.setFileId(fileId);
            collectionFileList.set(i, collectionFile);
        }

        // 加分布式锁，避免出现S锁和X锁循环等待死锁
        // 分布式锁过期时间
        int timeout = 20 * 1000;
        long time = System.currentTimeMillis() + timeout;
        // 加锁
        String collectionIdForLock = collectionId.concat("-deleteFileListByCollectionIdAndFileId");
        while (!redisLockService.lock(collectionIdForLock, String.valueOf(time))) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        collectionFileDao.deleteList(collectionFileList);
        // 修改项目组文件计数
        updateCollectionCountProperty(collectionId, CollectionEnum.UPDATE_FILE_COUNT, -collectionFileList.size());

        // 解锁
        redisLockService.unlock(collectionIdForLock, String.valueOf(time));
    }
}
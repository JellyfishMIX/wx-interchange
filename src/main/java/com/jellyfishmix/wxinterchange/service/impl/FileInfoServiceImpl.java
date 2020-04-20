package com.jellyfishmix.wxinterchange.service.impl;

import com.jellyfishmix.wxinterchange.entity.FileInfo;
import com.jellyfishmix.wxinterchange.dao.FileInfoDao;
import com.jellyfishmix.wxinterchange.service.FileInfoService;
import com.jellyfishmix.wxinterchange.utils.UniqueKeyUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 文件信息表(FileInfo)表服务实现类
 *
 * @author makejava
 * @since 2020-04-19 23:26:14
 */
@Service("fileInfoService")
public class FileInfoServiceImpl implements FileInfoService {
    @Resource
    private FileInfoDao fileInfoDao;

    /**
     * 通过fileId查询单条数据
     *
     * @param fileId 文件fileId
     * @return 实例对象
     */
    @Override
    public FileInfo queryByFileId(String fileId) {
        return this.fileInfoDao.queryByFileId(fileId);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<FileInfo> queryAllByLimit(int offset, int limit) {
        return this.fileInfoDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param fileInfo 实例对象
     * @return 实例对象
     */
    @Override
    public FileInfo insert(FileInfo fileInfo) {
        fileInfo.setFileId(UniqueKeyUtil.getUniqueKey());
        this.fileInfoDao.insert(fileInfo);
        fileInfo = fileInfoDao.queryByFileId(fileInfo.getFileId());
        return fileInfo;
    }

    /**
     * 修改数据
     *
     * @param fileInfo 实例对象
     * @return 实例对象
     */
    @Override
    public FileInfo update(FileInfo fileInfo) {
        this.fileInfoDao.update(fileInfo);
        return this.queryByFileId(fileInfo.getFileId());
    }

    /**
     * 通过fileId删除数据
     *
     * @param fileId 文件fileId
     * @return 是否成功
     */
    @Override
    public boolean deleteByFileId(String fileId) {
        return this.fileInfoDao.deleteByFileId(fileId) > 0;
    }
}
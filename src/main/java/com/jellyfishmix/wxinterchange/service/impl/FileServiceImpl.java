package com.jellyfishmix.wxinterchange.service.impl;

import com.jellyfishmix.wxinterchange.config.QiniuConfig;
import com.jellyfishmix.wxinterchange.dao.TeamAvatarDao;
import com.jellyfishmix.wxinterchange.dto.FileInfoDTO;
import com.jellyfishmix.wxinterchange.dao.FileInfoDao;
import com.jellyfishmix.wxinterchange.entity.FileInfo;
import com.jellyfishmix.wxinterchange.entity.TeamAvatar;
import com.jellyfishmix.wxinterchange.service.FileService;
import com.qiniu.common.QiniuException;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 文件信息表(FileInfo)表服务实现类
 *
 * @author makejava
 * @since 2020-04-19 23:26:14
 */
@Service("fileService")
public class FileServiceImpl implements FileService {
    @Resource
    private FileInfoDao fileInfoDao;
    @Resource
    private TeamAvatarDao teamAvatarDao;
    @Autowired
    private QiniuConfig qiniuConfig;

    /**
     * 通过fileId查询单条数据
     *
     * @param fileId 文件fileId
     * @return 实例对象
     */
    @Override
    public FileInfoDTO queryByFileId(String fileId) {
        return fileInfoDao.queryByFileId(fileId);
    }

    /**
     * 通过fileId列表查询文件信息列表
     *
     * @param fileInfoList 列表
     * @return
     */
    @Override
    public List<FileInfoDTO> queryListByFileIdList(List<FileInfo> fileInfoList) {
        return fileInfoDao.queryDTOListByFileId(fileInfoList);
    }

    /**
     * 从七牛云bucket中删除文件
     *
     * @param fileHash 全局唯一的文件Hash值，用来检查是否可以从七牛云bucket中删除
     * @param fileKey 文件fileKey
     */
    @Override
    public void deleteFromQiniuBucket(String fileHash, String fileKey) {
        // 查询fileHash是否在file_info表和team_avatar表中还存在，还存在则不能删。因为同样的hash对应七牛云的同一个文件。
        List<FileInfoDTO> fileInfoDTOListForCheck = fileInfoDao.queryByFileHash(fileHash);
        List<TeamAvatar> teamAvatarListForCheck = teamAvatarDao.queryByFileHash(fileHash);
        if (fileInfoDTOListForCheck.size() > 0 || teamAvatarListForCheck.size() > 0) {
            return;
        }

        Configuration qiniuConfiguration = new Configuration(Region.region1());
        Auth auth = Auth.create(qiniuConfig.getAccessKey(), qiniuConfig.getSecretKey());
        BucketManager bucketManager = new BucketManager(auth, qiniuConfiguration);
        try {
            bucketManager.delete(qiniuConfig.getBucketName(), fileKey);
        } catch (QiniuException e) {
            System.err.println(e.code());
            System.err.println(e.response.toString());
        }
    }
}
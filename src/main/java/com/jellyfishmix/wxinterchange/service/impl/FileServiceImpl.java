package com.jellyfishmix.wxinterchange.service.impl;

import com.jellyfishmix.wxinterchange.config.QiniuConfig;
import com.jellyfishmix.wxinterchange.dto.FileInfoDTO;
import com.jellyfishmix.wxinterchange.dao.FileInfoDao;
import com.jellyfishmix.wxinterchange.service.FileService;
import com.qiniu.common.QiniuException;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
        return this.fileInfoDao.queryByFileId(fileId);
    }

    /**
     * 从七牛云bucket中删除文件
     *
     * @param fileKey 文件fileKey
     */
    @Override
    public void deleteFromQiniuBucket(String fileKey) {
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
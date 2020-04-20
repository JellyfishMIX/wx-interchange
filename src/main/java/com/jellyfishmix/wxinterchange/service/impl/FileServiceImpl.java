package com.jellyfishmix.wxinterchange.service.impl;

import com.jellyfishmix.wxinterchange.entity.FileInfo;
import com.jellyfishmix.wxinterchange.dao.FileInfoDao;
import com.jellyfishmix.wxinterchange.service.FileService;
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
}
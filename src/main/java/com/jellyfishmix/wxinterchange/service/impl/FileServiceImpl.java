package com.jellyfishmix.wxinterchange.service.impl;

import com.jellyfishmix.wxinterchange.dao.TeamFileDao;
import com.jellyfishmix.wxinterchange.entity.FileInfo;
import com.jellyfishmix.wxinterchange.dao.FileInfoDao;
import com.jellyfishmix.wxinterchange.entity.TeamFile;
import com.jellyfishmix.wxinterchange.service.FileService;
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
public class FileServiceImpl implements FileService {
    @Resource
    private FileInfoDao fileInfoDao;
    @Resource
    private TeamFileDao teamFileDao;

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
     * 查询项目组文件列表，通过上传日期排序
     *
     * @param tid 项目组tid
     * @param pageIndex 页码
     * @param pageSize 每页容量
     * @return
     */
    @Override
    public List<TeamFile> queryTeamFileListOrderByCreationTime(String tid, int pageIndex, int pageSize) {
        List<TeamFile> teamFileList = teamFileDao.queryTeamFileListOrderByCreationTime(tid, pageIndex, pageSize);
        return teamFileList;
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
    public FileInfo insert(FileInfo fileInfo, TeamFile teamFile) {
        fileInfo.setFileId(UniqueKeyUtil.getUniqueKey());
        this.fileInfoDao.insert(fileInfo);
        this.teamFileDao.insert(teamFile);
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
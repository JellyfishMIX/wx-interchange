package com.jellyfishmix.wxinterchange.service;

import com.jellyfishmix.wxinterchange.entity.FileInfo;
import com.jellyfishmix.wxinterchange.entity.TeamFile;

import java.util.List;

/**
 * 文件信息表(FileInfo)表服务接口
 *
 * @author makejava
 * @since 2020-04-19 23:26:14
 */
public interface FileService {

    /**
     * 通过fileId查询单条数据
     *
     * @param fileId 文件fileId
     * @return 实例对象
     */
    FileInfo queryByFileId(String fileId);

    /**
     * 查询项目组文件列表，通过上传日期排序
     *
     * @param tid 项目组tid
     * @param pageIndex 页码
     * @param pageSize 每页容量
     * @return
     */
    List<TeamFile> queryTeamFileListOrderByCreationTime(String tid, int pageIndex, int pageSize);

    /**
     * 修改数据
     *
     * @param fileInfo 实例对象
     * @return 实例对象
     */
    FileInfo update(FileInfo fileInfo);

    /**
     * 通过fileId删除数据
     *
     * @param fileId 文件fileId
     * @return 是否成功
     */
    boolean deleteByFileId(String fileId);

}
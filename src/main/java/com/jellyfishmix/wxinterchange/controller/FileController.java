package com.jellyfishmix.wxinterchange.controller;

import com.jellyfishmix.wxinterchange.config.QiniuConfig;
import com.jellyfishmix.wxinterchange.entity.FileInfo;
import com.jellyfishmix.wxinterchange.entity.TeamFile;
import com.jellyfishmix.wxinterchange.enums.FileEnum;
import com.jellyfishmix.wxinterchange.service.FileInfoService;
import com.jellyfishmix.wxinterchange.service.TeamFileService;
import com.jellyfishmix.wxinterchange.utils.ResultVOUtil;
import com.jellyfishmix.wxinterchange.vo.ResultVO;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author JellyfishMIX
 * @date 2020/4/17 10:03 上午
 */
@RestController
@RequestMapping("/file")
public class FileController {
    @Autowired
    private QiniuConfig qiniuConfig;
    @Autowired
    private FileInfoService fileInfoService;
    @Autowired
    private TeamFileService teamFileService;

    /**
     * 获取七牛云文件上传upToken
     *
     * @return
     */
    @GetMapping("/get_upload_token")
    public Map<String, Object> getUploadToken() {
        HashMap hashMap = new HashMap(2);
        Auth auth = Auth.create(qiniuConfig.getAccessKey(), qiniuConfig.getSecretKey());
        // 自定义upToken上传策略，返回key，hash，fsize(fileSize), mimeType
        // 有关upToken更多信息，请查看：https://developer.qiniu.com/kodo/sdk/1239/java
        StringMap putPolicy = new StringMap();
        putPolicy.put("returnBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"fsize\":$(fsize),\"mimeType\":$(mimeType)}");
        // upToken生存时间
        long expireSeconds = 3600;

        String upToken = auth.uploadToken(qiniuConfig.getBucketName(), null, expireSeconds, putPolicy);
        hashMap.put("success", true);
        hashMap.put("uptoken", upToken);
        return  hashMap;
    }

    /**
     * 向项目组上传文件（.pdf, .docx, .xlsx, .pptx等任意格式的文件）
     *
     * @param tid 上传至tid群组
     * @param uid 上传者uid
     * @param fileKey 文件资源key
     * @param fileHash 全局唯一的文件hash值
     * @param fileName 文件名
     * @param fileUrl 文件资源URL
     * @param fileSize 文件大小（单位为b）
     * @param mimeType 文件类型
     * @return
     */
    @PostMapping("/upload_file_to_team")
    public ResultVO uploadFileToTeam(@RequestParam("tid") String tid,
                                     @RequestParam("uid") String uid,
                                     @RequestParam("key") String fileKey,
                                     @RequestParam("hash") String fileHash,
                                     @RequestParam("fileName") String fileName,
                                     @RequestParam("fileUrl") String fileUrl,
                                     @RequestParam("fileSize") Integer fileSize,
                                     @RequestParam("mimeType") String mimeType) {
        FileInfo fileInfo = new FileInfo();
        fileInfo.setFileKey(fileKey);
        fileInfo.setFileHash(fileHash);
        fileInfo.setFileName(fileName);
        fileInfo.setFileUrl(fileUrl);
        fileInfo.setFileSize(fileSize);
        fileInfo.setMimeType(mimeType);
        fileInfo.setUid(uid);

        fileInfo = fileInfoService.insert(fileInfo);

        TeamFile teamFile = new TeamFile();
        teamFile.setTid(tid);
        teamFile.setFileId(fileInfo.getFileId());
        teamFile.setUid(uid);
        teamFileService.insert(teamFile);
        return ResultVOUtil.success(FileEnum.SUCCESS.getStateCode(), FileEnum.SUCCESS.getStateMsg(), fileInfo);
    }

    /**
     * 通过tid查询项目组内的文件列表，分页
     *
     * @param tid 项目组tid
     * @param page 页码
     * @param pageSize 每页的容量
     * @return
     */
    public ResultVO queryTeamFileListByTid(@RequestParam("tid") String tid,
                                           @RequestParam("page") Integer page,
                                           @RequestParam("pageSize") Integer pageSize) {
        List<TeamFile> teamFileList;
        return null;
    }
}
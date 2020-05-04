package com.jellyfishmix.wxinterchange.controller;

import com.jellyfishmix.wxinterchange.config.QiniuConfig;
import com.jellyfishmix.wxinterchange.converter.JSONArrayToListConverter;
import com.jellyfishmix.wxinterchange.dto.FileInfoDTO;
import com.jellyfishmix.wxinterchange.entity.FileInfo;
import com.jellyfishmix.wxinterchange.enums.FileEnum;
import com.jellyfishmix.wxinterchange.service.FileService;
import com.jellyfishmix.wxinterchange.utils.ResultVOUtil;
import com.jellyfishmix.wxinterchange.vo.ResultVO;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import org.json.JSONArray;
import org.json.JSONObject;
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
    private FileService fileService;

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
        return hashMap;
    }

    /**
     * 通过fileId查询文件信息
     *
     * @param fileId 文件fileId
     * @return
     */
    @GetMapping("/query_file_info_by_file_id")
    public ResultVO queryFileInfoByFileId(@RequestParam("fileId") String fileId) {
        FileInfoDTO fileInfoDTO = fileService.queryByFileId(fileId);
        return ResultVOUtil.success(FileEnum.SUCCESS.getStateCode(), FileEnum.SUCCESS.getStateMsg(), fileInfoDTO);
    }

    /**
     * 通过fileId列表查询文件信息列表
     *
     * @param jsonStr jsonStr
     * @return
     */
    @GetMapping("/query_file_info_list_by_file_id_list")
    public ResultVO queryFileInfoListByFileIdList(@RequestBody String jsonStr) {
        JSONObject jsonObject = new JSONObject(jsonStr);
        JSONArray fileInfoJSONArray = jsonObject.getJSONArray("fileInfoList");
        List<FileInfo> fileInfoList = JSONArrayToListConverter.convertToFileIdList(fileInfoJSONArray);
        List<FileInfoDTO> fileInfoDTOList = fileService.queryListByFileIdList(fileInfoList);
        return ResultVOUtil.success(FileEnum.SUCCESS.getStateCode(), FileEnum.SUCCESS.getStateMsg(), fileInfoDTOList);
    }
}
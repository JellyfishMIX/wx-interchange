package com.jellyfishmix.wxinterchange.converter;

import com.jellyfishmix.wxinterchange.entity.FileInfo;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JellyfishMIX
 * @date 2020/4/27 10:27 上午
 */
public class JSONArrayToListConverter {
    public static List<FileInfo> convertToFileInfoList(JSONArray jsonArray) {
        List<FileInfo> fileInfoList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonArrayElement = jsonArray.getJSONObject(i);
            FileInfo fileInfo = new FileInfo();
            for (int j = 0; j < jsonArrayElement.length(); j++) {
                fileInfo.setFileKey(jsonArrayElement.getString("fileKey"));
                fileInfo.setFileHash(jsonArrayElement.getString("fileHash"));
                fileInfo.setFileName(jsonArrayElement.getString("fileName"));
                fileInfo.setFileUrl(jsonArrayElement.getString("fileUrl"));
                fileInfo.setFileSize(jsonArrayElement.getInt("fileSize"));
                fileInfo.setMimeType(jsonArrayElement.getString("mimeType"));
            }
            fileInfoList.add(fileInfo);
        }
        return fileInfoList;
    }

    public static List<FileInfo> convertToTidList(JSONArray jsonArray) {
        List<FileInfo> fileInfoList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonArrayElement = jsonArray.getJSONObject(i);
            FileInfo fileInfo = new FileInfo();
            for (int j = 0; j < jsonArrayElement.length(); j++) {
                fileInfo.setFileId(jsonArrayElement.getString("fileId"));
            }
            fileInfoList.add(fileInfo);
        }
        return fileInfoList;
    }
}

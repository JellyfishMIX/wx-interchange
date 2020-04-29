package com.jellyfishmix.wxinterchange.controller;

import com.jellyfishmix.wxinterchange.converter.JSONArrayToListConverter;
import com.jellyfishmix.wxinterchange.entity.CollectionFile;
import com.jellyfishmix.wxinterchange.enums.CollectionEnum;
import com.jellyfishmix.wxinterchange.service.CollectionService;
import com.jellyfishmix.wxinterchange.utils.ResultVOUtil;
import com.jellyfishmix.wxinterchange.vo.ResultVO;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author JellyfishMIX
 * @date 2020/4/29 5:40 下午
 */
@RestController
@RequestMapping("/collection")
public class CollectionController {
    @Autowired
    private CollectionService collectionService;

    @PostMapping("/added_file_list_from_team")
    public ResultVO addedFileListFromTeam(@RequestBody String josnStr) {
        JSONObject jsonObject = new JSONObject(josnStr);
        String uid = jsonObject.getString("uid");
        JSONArray collectionFileJSONArray = jsonObject.getJSONArray("collectionFileList");
        List<CollectionFile> collectionFileList = JSONArrayToListConverter.convertToCollectionFileList(collectionFileJSONArray);
        collectionService.addedFileList(uid, collectionFileList);
        return ResultVOUtil.success(CollectionEnum.SUCCESS.getStateCode(), CollectionEnum.SUCCESS.getStateMsg());
    }
}
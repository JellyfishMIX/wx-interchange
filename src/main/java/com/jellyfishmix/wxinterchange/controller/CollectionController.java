package com.jellyfishmix.wxinterchange.controller;

import com.jellyfishmix.wxinterchange.converter.JSONArrayToListConverter;
import com.jellyfishmix.wxinterchange.dto.CollectionFileDTO;
import com.jellyfishmix.wxinterchange.entity.CollectionFile;
import com.jellyfishmix.wxinterchange.enums.CollectionEnum;
import com.jellyfishmix.wxinterchange.service.CollectionService;
import com.jellyfishmix.wxinterchange.utils.ResultVOUtil;
import com.jellyfishmix.wxinterchange.vo.ResultVO;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 通过uid查询收藏集文件列表
     *
     * @param uid 用户uid
     * @param pageIndex 页码，从1开始
     * @param pageSize 每页行数
     * @return
     */
    @GetMapping("/query_file_list_by_uid")
    public ResultVO queryFileListByUid(@RequestParam("uid") String uid,
                                       @RequestParam("pageIndex") int pageIndex,
                                       @RequestParam("pageSize") int pageSize) {
        List<CollectionFileDTO> collectionFileDTOList = collectionService.queryFileList(uid, pageIndex, pageSize);
        return ResultVOUtil.success(CollectionEnum.SUCCESS.getStateCode(), CollectionEnum.SUCCESS.getStateMsg(), collectionFileDTOList);
    }

    /**
     * 从项目组添加文件列表到收藏集
     *
     * @param jsonStr JSON字符串
     * @return
     */
    @PostMapping("/added_file_list_from_team")
    public ResultVO addedFileListFromTeam(@RequestBody String jsonStr) {
        JSONObject jsonObject = new JSONObject(jsonStr);
        String uid = jsonObject.getString("uid");
        JSONArray collectionFileJSONArray = jsonObject.getJSONArray("collectionFileList");
        List<CollectionFile> collectionFileList = JSONArrayToListConverter.convertToCollectionFileList(collectionFileJSONArray);
        collectionService.addedFileList(uid, collectionFileList);
        return ResultVOUtil.success(CollectionEnum.SUCCESS.getStateCode(), CollectionEnum.SUCCESS.getStateMsg());
    }

    /**
     * 删除文件（多个）
     *
     * @param jsonStr JSON字符串
     * @return
     */
    @PostMapping("/delete_file_list")
    public ResultVO deleteFileList(@RequestBody String jsonStr) {
        JSONObject jsonObject = new JSONObject(jsonStr);
        String collectionId = jsonObject.getString("collectionId");
        List<Integer> idList = (List<Integer>)(Object) jsonObject.getJSONArray("idList").toList();
        collectionService.deleteFileList(collectionId, idList);
        return ResultVOUtil.success(CollectionEnum.SUCCESS.getStateCode(), CollectionEnum.SUCCESS.getStateMsg());
    }
}
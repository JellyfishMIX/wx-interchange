package com.jellyfishmix.wxinterchange.controller;

import com.jellyfishmix.wxinterchange.converter.JSONArrayToListConverter;
import com.jellyfishmix.wxinterchange.dto.*;
import com.jellyfishmix.wxinterchange.entity.FileInfo;
import com.jellyfishmix.wxinterchange.entity.TeamAvatar;
import com.jellyfishmix.wxinterchange.entity.TeamInfo;
import com.jellyfishmix.wxinterchange.entity.TeamUser;
import com.jellyfishmix.wxinterchange.enums.FileEnum;
import com.jellyfishmix.wxinterchange.enums.TeamEnum;
import com.jellyfishmix.wxinterchange.enums.UserEnum;
import com.jellyfishmix.wxinterchange.service.FileStatisticsService;
import com.jellyfishmix.wxinterchange.service.RedisService;
import com.jellyfishmix.wxinterchange.service.TeamService;
import com.jellyfishmix.wxinterchange.service.UserService;
import com.jellyfishmix.wxinterchange.utils.ResultVOUtil;
import com.jellyfishmix.wxinterchange.vo.ResultVO;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author JellyfishMIX
 * @date 2020/4/11 10:52 上午
 */
@RestController
@RequestMapping("/team")
public class TeamController {
    @Autowired
    private TeamService teamService;
    @Autowired
    private UserService userService;
    @Autowired
    private FileStatisticsService fileStatisticsService;

    /**
     * 创建项目组
     *
     * @param uid           创建者uid
     * @param teamName      项目组名称
     * @param teamAvatarUrl 项目组头像URL
     * @param teamGrade     项目组等级，官方项目组为1，普通项目组为2，保留0
     * @return
     */
    @PostMapping("/create_team")
    public ResultVO createTeam(@RequestParam("uid") String uid,
                               @RequestParam("teamName") String teamName,
                               @RequestParam("teamAvatarUrl") String teamAvatarUrl,
                               @RequestParam("teamGrade") Integer teamGrade) {
        // 校验uid是否存在
        UserInfoDTO userInfoDTO = userService.queryByUid(uid);
        if (userInfoDTO.getStateCode().equals(UserEnum.USER_INFO_NULL.getStateCode())) {
            return ResultVOUtil.fail(UserEnum.USER_INFO_NULL.getStateCode(), UserEnum.USER_INFO_NULL.getStateMsg());
        }

        TeamInfo teamInfo = new TeamInfo();
        teamInfo.setTeamName(teamName);
        teamInfo.setAvatarUrl(teamAvatarUrl);
        teamInfo.setGrade(teamGrade);

        // teamUser表中添加记录
        TeamUser teamUser = new TeamUser();
        teamUser.setUid(uid);
        // userGrade，1 为创建者等级
        teamUser.setUserGrade(1);

        teamInfo = teamService.createTeam(teamInfo, teamUser);

        return ResultVOUtil.success(TeamEnum.SUCCESS.getStateCode(), TeamEnum.SUCCESS.getStateMsg(), teamInfo);
    }

    /**
     * 通过tid查询teamInfo
     *
     * @param tid 项目组tid
     * @return
     */
    @GetMapping("/query_team_info_by_tid")
    public ResultVO queryTeamInfoByTid(@RequestParam("tid") String tid) {
        TeamInfoDTO teamInfoDTO = teamService.queryTeamInfoByTid(tid);
        // 查询teamInfo为null
        if (teamInfoDTO.getStateCode().equals(TeamEnum.TEAM_INFO_NULL.getStateCode())) {
            return ResultVOUtil.fail(TeamEnum.TEAM_INFO_NULL.getStateCode(), TeamEnum.TEAM_INFO_NULL.getStateMsg());
        }
        TeamInfo teamInfo = teamInfoDTO.getTeamInfo();
        return ResultVOUtil.success(TeamEnum.SUCCESS.getStateCode(), TeamEnum.SUCCESS.getStateMsg(), teamInfo);
    }

    /**
     * 通过tid查询项目组成员列表
     *
     * @param tid 项目组tid
     * @param pageIndex 页码，从1开始
     * @param pageSize  每页行数
     * @return
     */
    @GetMapping("/query_team_user_list_by_tid")
    public ResultVO queryTeamUserListByTid(@RequestParam("tid") String tid,
                                           @RequestParam("pageIndex") Integer pageIndex,
                                           @RequestParam("pageSize") Integer pageSize) {
        List<TeamUserDTO> teamUserDTOList = teamService.queryTeamUserListByTid(tid, pageIndex, pageSize);
        return ResultVOUtil.success(TeamEnum.SUCCESS.getStateCode(), TeamEnum.SUCCESS.getStateMsg(), teamUserDTOList);
    }

    /**
     * 获取官方项目组列表
     * 内部API
     *
     * @return
     */
    @GetMapping("/query_official_team_list")
    public ResultVO queryOfficialTeamList() {
        List<TeamInfo> teamInfoList = teamService.queryOfficialTeamList();
        return ResultVOUtil.success(TeamEnum.SUCCESS.getStateCode(), TeamEnum.SUCCESS.getStateMsg(), teamInfoList);
    }

    /**
     * 通过tid查询项目组内的文件列表，分页
     *
     * @param tid       项目组tid
     * @param pageIndex 页码，从1开始
     * @param pageSize  每页的行数
     * @return
     */
    @GetMapping("/query_team_file_list_order_by_creation_time")
    public ResultVO queryTeamFileListOrderByCreationTime(@RequestParam("tid") String tid,
                                                         @RequestParam("pageIndex") Integer pageIndex,
                                                         @RequestParam("pageSize") Integer pageSize) {
        List<TeamFileDTO> teamFileDTOList = teamService.queryTeamFileListOrderByCreationTime(tid, pageIndex, pageSize);
        return ResultVOUtil.success(FileEnum.SUCCESS.getStateCode(), FileEnum.SUCCESS.getStateMsg(), teamFileDTOList);
    }

    /**
     * 查询单个项目组成员等级
     *
     * @param tid 项目组tid
     * @param uid 用户uid
     * @return
     */
    @GetMapping("/query_user_grade")
    public ResultVO queryUserGrade(@RequestParam("tid") String tid,
                                   @RequestParam("uid") String uid) {
        TeamUser teamUser = teamService.queryOneTeamUser(tid, uid);
        return ResultVOUtil.success(TeamEnum.SUCCESS.getStateCode(), TeamEnum.SUCCESS.getStateMsg(), teamUser);
    }

    /**
     * 通过关键词搜索项目组内的文件
     *
     * @param jsonStr jsonStr
     * @return
     */
    @PostMapping("/search_team_file_list_by_keyword")
    public ResultVO searchTeamFileListByKeyword(@RequestBody String jsonStr) {
        JSONObject jsonObject = new JSONObject(jsonStr);
        JSONArray jsonArray = jsonObject.getJSONArray("tidList");
        List<String> tidList = JSONArrayToListConverter.convertToStringList(jsonArray);
        String keyword = jsonObject.getString("keyword");
        int pageIndex = jsonObject.getInt("pageIndex");
        int pageSize = jsonObject.getInt("pageSize");
        List<TeamFileDTO> teamFileDTOList = teamService.searchTeamFileListByKeyword(tidList, keyword, pageIndex, pageSize);
        return ResultVOUtil.success(TeamEnum.SUCCESS.getStateCode(), TeamEnum.SUCCESS.getStateMsg(), teamFileDTOList);
    }

    /**
     * 向项目组上传文件（.pdf, .docx, .xlsx, .pptx等任意格式的文件），支持多文件
     *
     * @param jsonStr jsonStr
     * @return
     */
    @PostMapping("/upload_file_list_to_team")
    public ResultVO uploadFileToTeam(@RequestBody String jsonStr) {
        JSONObject jsonObject = new JSONObject(jsonStr);
        JSONArray fileInfoJsonArray = jsonObject.getJSONArray("fileInfoList");
        List<FileInfo> fileInfoList = JSONArrayToListConverter.convertToFileInfoList(fileInfoJsonArray);
        String tid = jsonObject.getString("tid");
        String uid = jsonObject.getString("uid");
        teamService.uploadFileListToTeam(tid, uid, fileInfoList);

        // 埋点系统文件统计
        fileStatisticsService.updateInstantChangedQuantity(fileInfoList.size());
        return ResultVOUtil.success(TeamEnum.SUCCESS.getStateCode(), TeamEnum.SUCCESS.getStateMsg());
    }

    /**
     * 将fileId文件列表添加至项目组
     *
     * @param jsonStr jsonStr
     * @return
     */
    @RequestMapping("/add_file_list_to_team_by_file_id_list")
    public ResultVO addFileListToTeamByFileIdList(@RequestBody String jsonStr) {
        JSONObject jsonObject = new JSONObject(jsonStr);
        JSONArray fileInfoJsonArray = jsonObject.getJSONArray("fileInfoList");
        List<FileInfo> fileInfoList = JSONArrayToListConverter.convertToFileIdList(fileInfoJsonArray);
        String tid = jsonObject.getString("tid");
        String uid = jsonObject.getString("uid");
        teamService.addFileListToTeamByFileIdList(tid, uid, fileInfoList);
        return ResultVOUtil.success(TeamEnum.SUCCESS.getStateCode(), TeamEnum.SUCCESS.getStateMsg());
    }

    /**
     * 向项目组上传项目组头像文件（和“修改项目组信息”组合使用）
     *
     * @param tid      tid项目组tid
     * @param uid      上传者uid
     * @param fileKey  文件fileKey
     * @param fileHash 文件fileHash
     * @param fileName 文件fileName
     * @param fileUrl  文件URL
     * @param fileSize 文件大小（以b为单位）
     * @param mimeType 文件类型
     * @return
     */
    @PostMapping("/upload_team_avatar")
    public ResultVO uploadTeamAvatar(@RequestParam("tid") String tid,
                                     @RequestParam("uid") String uid,
                                     @RequestParam("fileKey") String fileKey,
                                     @RequestParam("fileHash") String fileHash,
                                     @RequestParam("fileName") String fileName,
                                     @RequestParam("fileUrl") String fileUrl,
                                     @RequestParam("fileSize") Integer fileSize,
                                     @RequestParam("mimeType") String mimeType) {
        TeamAvatar teamAvatar = new TeamAvatar();
        teamAvatar.setTid(tid);
        teamAvatar.setFileKey(fileKey);
        teamAvatar.setFileHash(fileHash);
        teamAvatar.setFileName(fileName);
        teamAvatar.setFileUrl(fileUrl);
        teamAvatar.setFileSize(fileSize);
        teamAvatar.setMimeType(mimeType);
        teamAvatar.setUid(uid);
        teamService.uploadTeamAvatar(tid, teamAvatar);
        return ResultVOUtil.success(TeamEnum.SUCCESS.getStateCode(), TeamEnum.SUCCESS.getStateMsg(), teamAvatar);
    }

    /**
     * 修改项目组信息
     *
     * @param tid              将要修改的项目组的tid
     * @param newTeamName      新项目组名称，非必须
     * @param newTeamAvatarUrl 新项目组头像URL，非必须
     * @return
     */
    @PostMapping("/update_team_info")
    public ResultVO updateTeamInfo(@RequestParam("tid") String tid,
                                   @RequestParam(value = "newTeamName", required = false) String newTeamName,
                                   @RequestParam(value = "newTeamAvatarUrl", required = false) String newTeamAvatarUrl) {
        TeamInfo teamInfo = new TeamInfo();
        teamInfo.setTid(tid);
        if (newTeamName != null) {
            teamInfo.setTeamName(newTeamName);
        }
        if (newTeamAvatarUrl != null) {
            teamInfo.setAvatarUrl(newTeamAvatarUrl);
        }
        if (newTeamName == null && newTeamAvatarUrl == null) {
            return ResultVOUtil.fail(TeamEnum.TEAM_INFO_PARAM_NULL.getStateCode(), TeamEnum.TEAM_INFO_PARAM_NULL.getStateMsg());
        }
        TeamInfo teamInfoFromQuery = teamService.updateTeamInfoWithoutQuery(teamInfo);
        return ResultVOUtil.success(TeamEnum.SUCCESS.getStateCode(), TeamEnum.SUCCESS.getStateMsg(), teamInfoFromQuery);
    }

    /**
     * 进入项目组
     *
     * @param tid 项目组tid
     * @param uid 用户uid
     * @return
     */
    @PostMapping("/enter_team")
    public ResultVO enterTeam(@RequestParam("tid") String tid,
                              @RequestParam("uid") String uid) {
        TeamInfoDTO teamInfoDTO = null;
        // 检查uid用户是否已加入了tid项目组
        TeamUser teamUser = teamService.queryTeamUserByTidAndUid(tid, uid);
        // 如果未加入，则将此uid用户加入tid对应的项目组
        if (teamUser == null) {
            teamInfoDTO = teamService.joinTeam(tid, uid);
            // teamInfoDTO判业务异常逻辑先不写
            return ResultVOUtil.success(TeamEnum.SUCCESS.getStateCode(), TeamEnum.SUCCESS.getStateMsg(), teamInfoDTO.getTeamInfo());
        }

        teamInfoDTO = teamService.queryTeamInfoByTid(tid);
        return ResultVOUtil.success(TeamEnum.SUCCESS.getStateCode(), TeamEnum.SUCCESS.getStateMsg(), teamInfoDTO.getTeamInfo());
    }

    /**
     * 修改tid为官方项目组
     * 内部API
     *
     * @param tid 项目组tid
     * @return
     */
    @PostMapping("/change_to_official_team")
    public ResultVO changeToOfficialTeam(@RequestParam("tid") String tid) {
        teamService.changeToOfficialTeam(tid);
        return ResultVOUtil.success(TeamEnum.SUCCESS.getStateCode(), TeamEnum.SUCCESS.getStateMsg());
    }

    /**
     * 批量删除项目组内的文件
     *
     * @param jsonStr jsonStr
     * @return
     */
    @PostMapping("/delete_file_list_from_team")
    public ResultVO deleteFileFromTeam(@RequestBody String jsonStr) {
        JSONObject jsonObject = new JSONObject(jsonStr);
        JSONArray fileInfoJsonArray = jsonObject.getJSONArray("fileInfoList");
        List<FileInfo> fileInfoList = JSONArrayToListConverter.convertToFileIdList(fileInfoJsonArray);
        String tid = jsonObject.getString("tid");
        teamService.deleteFileListFromTeam(tid, fileInfoList);
        return ResultVOUtil.success(TeamEnum.SUCCESS.getStateCode(), TeamEnum.SUCCESS.getStateMsg());
    }

    /**
     * 删除项目组成员
     *
     * @param tid 项目组tid
     * @param uid 用户uid
     * @return
     */
    @PostMapping("/delete_team_user")
    public ResultVO deleteTeamUser(@RequestParam("tid") String tid,
                                   @RequestParam("uid") String uid) {
        TeamDTO teamDTO = teamService.deleteTeamUser(tid, uid);
        if (teamDTO.getStateCode().equals(TeamEnum.CREATED_NUMBER_DELETED_FAIL.getStateCode())) {
            return ResultVOUtil.fail(TeamEnum.CREATED_NUMBER_DELETED_FAIL.getStateCode(), TeamEnum.CREATED_NUMBER_DELETED_FAIL.getStateMsg());
        }
        return ResultVOUtil.success(TeamEnum.SUCCESS.getStateCode(), TeamEnum.SUCCESS.getStateMsg());
    }

    /**
     * 解散项目组
     *
     * @param uid 操作用户uid，必须是tid的creator
     * @param tid 项目组tid
     * @return
     */
    @PostMapping("/disband_group")
    public ResultVO disbandGroup(@RequestParam("uid") String uid,
                                 @RequestParam("tid") String tid) {
        TeamDTO teamDTO = teamService.disbandGroup(uid, tid);
        if (teamDTO.getStateCode().equals(TeamEnum.PERMISSION_DENIED.getStateCode())) {
            return ResultVOUtil.fail(TeamEnum.PERMISSION_DENIED.getStateCode(), TeamEnum.PERMISSION_DENIED.getStateMsg());
        }
        return ResultVOUtil.success(TeamEnum.SUCCESS.getStateCode(), TeamEnum.SUCCESS.getStateMsg());
    }
}

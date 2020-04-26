package com.jellyfishmix.wxinterchange.controller;

import com.jellyfishmix.wxinterchange.dto.*;
import com.jellyfishmix.wxinterchange.entity.FileInfo;
import com.jellyfishmix.wxinterchange.entity.TeamFile;
import com.jellyfishmix.wxinterchange.entity.TeamInfo;
import com.jellyfishmix.wxinterchange.entity.TeamUser;
import com.jellyfishmix.wxinterchange.enums.FileEnum;
import com.jellyfishmix.wxinterchange.enums.TeamEnum;
import com.jellyfishmix.wxinterchange.enums.UserEnum;
import com.jellyfishmix.wxinterchange.service.TeamService;
import com.jellyfishmix.wxinterchange.service.UserService;
import com.jellyfishmix.wxinterchange.utils.ResultVOUtil;
import com.jellyfishmix.wxinterchange.vo.ResultVO;
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

    /**
     * 创建项目组
     *
     * @param uid 创建者uid
     * @param teamName 项目组名称
     * @param teamAvatarUrl 项目组头像URL
     * @param teamGrade 项目组等级，官方项目组为1，普通项目组为2，保留0
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
     * @return
     */
    @GetMapping("/query_team_user_list_by_tid")
    public ResultVO queryTeamUserListByTid(@RequestParam("tid") String tid) {
        List<TeamUserDTO> teamUserDTOList = teamService.queryTeamUserListByTid(tid);
        return ResultVOUtil.success(TeamEnum.SUCCESS.getStateCode(), TeamEnum.SUCCESS.getStateMsg(), teamUserDTOList);
    }

    /**
     * 获取官方项目组列表
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
     * @param tid 项目组tid
     * @param pageIndex 页码，从1开始
     * @param pageSize 每页的行数
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
     * 通过关键词搜索项目组内的文件
     *
     * @param tid 项目组tid
     * @param keyword 关键词
     * @param pageIndex 页码，从1开始
     * @param pageSize 每页行数
     * @return
     */
    @GetMapping("/search_team_file_list_by_keyword")
    public ResultVO searchTeamFileListByKeyword(@RequestParam("tid") String tid,
                                                @RequestParam("keyword") String keyword,
                                                @RequestParam("pageIndex") int pageIndex,
                                                @RequestParam("pageSize") int pageSize) {
        List<TeamFileDTO> teamFileDTOList = teamService.searchTeamFileListByKeyword(tid, keyword, pageIndex, pageSize);
        return ResultVOUtil.success(TeamEnum.SUCCESS.getStateCode(), TeamEnum.SUCCESS.getStateMsg(), teamFileDTOList);
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

        TeamFile teamFile = new TeamFile();
        teamFile.setTid(tid);
        teamFile.setFileId(fileInfo.getFileId());
        teamFile.setUid(uid);

        FileInfoDTO fileInfoDTO = teamService.uploadFileToTeam(fileInfo, teamFile);
        return ResultVOUtil.success(FileEnum.SUCCESS.getStateCode(), FileEnum.SUCCESS.getStateMsg(), fileInfoDTO);
    }

    /**
     * 修改项目组名称
     *
     * @param tid 将要修改的项目组的tid
     * @param newTeamName 新项目组名称
     * @return
     */
    @PostMapping("/update_team_name")
    public ResultVO updateTeamName(@RequestParam("tid") String tid,
                                   @RequestParam("newTeamName") String newTeamName) {
        TeamInfo teamInfo = new TeamInfo();
        teamInfo.setTid(tid);
        teamInfo.setTeamName(newTeamName);
        TeamInfoDTO teamInfoDTO = teamService.updateTeamInfo(teamInfo);
        // 如果tid对应的teamInfo为空
        if (teamInfoDTO.getStateCode().equals(TeamEnum.TEAM_INFO_NULL.getStateCode())) {
            return ResultVOUtil.fail(TeamEnum.TEAM_INFO_NULL.getStateCode(), TeamEnum.TEAM_INFO_NULL.getStateMsg());
        }
        return ResultVOUtil.success(TeamEnum.SUCCESS.getStateCode(), TeamEnum.SUCCESS.getStateMsg(), teamInfoDTO.getTeamInfo());
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
     * 删除项目组内的文件（单个）
     *
     * @param tid 项目组tid
     * @param fileId 文件fileId
     * @return
     */
    @PostMapping("/delete_file_from_team")
    public ResultVO deleteFileFromTeam(@RequestParam("tid") String tid,
                                       @RequestParam("fileId") String fileId) {
        teamService.deleteFileFromTeam(tid, fileId);
        return ResultVOUtil.success(TeamEnum.SUCCESS.getStateCode(), TeamEnum.SUCCESS.getStateMsg());
    }
}
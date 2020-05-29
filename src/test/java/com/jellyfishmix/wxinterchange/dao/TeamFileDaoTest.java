package com.jellyfishmix.wxinterchange.dao;

import com.jellyfishmix.wxinterchange.dto.TeamFileDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author JellyfishMIX
 * @date 2020/5/30 3:37 上午
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
class TeamFileDaoTest {
    @Resource
    TeamFileDao teamFileDao;

    @Test
    void queryTeamFileListByKeyword() {
        String[] tidArray = {"171e80398a3", "172090af735", "1723f8ccf74"};
        List<String> tidList = new ArrayList<>(Arrays.asList(tidArray));
        List<TeamFileDTO> teamFileDTOList = teamFileDao.queryTeamFileListByKeyword(tidList, "锤子", 0, 10);
        for (TeamFileDTO each : teamFileDTOList) {
            assertTrue(each.getFileName().contains("锤子"));
        }
    }
}
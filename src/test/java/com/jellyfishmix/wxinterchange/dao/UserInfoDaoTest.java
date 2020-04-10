package com.jellyfishmix.wxinterchange.dao;

import com.jellyfishmix.wxinterchange.entity.UserInfo;
import com.jellyfishmix.wxinterchange.utils.UniqueKeyUtil;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author JellyfishMIX
 * @date 2020/4/8 1:05 上午
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserInfoDaoTest {
    @Resource
    private UserInfoDao userInfoDao;

    @Test
    @Disabled
    void insertUserInfo() {
        UserInfo userInfo = new UserInfo();
        userInfo.setUid(UniqueKeyUtil.getUniqueKey());
        userInfo.setUsername("test_username");
        userInfo.setOpenid("test_openid-4");
        int result = userInfoDao.insertUserInfo(userInfo);
        assertEquals(1, result);
    }

    @Test
    @Disabled
    void updateUserInfo() {
        UserInfo userInfo = new UserInfo();
        userInfo.setUid("158628518796132710");
        userInfo.setUsername("test-7");
        int result = userInfoDao.updateUserInfo(userInfo);
        assertEquals(1, result);
    }

    @Test
    @Disabled
    void selectUserInfoByUid() {
        String uid = "158628505011784344";
        UserInfo resultUserInfo = userInfoDao.selectUserInfoByUid(uid);
        assertNotNull(resultUserInfo);
    }

    @Test
    @Disabled
    void selectUserInfoByOpenid() {
        String openid = "test_openid-1";
        UserInfo resultUserInfo = userInfoDao.selectUserInfoByOpenid(openid);
        assertNotNull(resultUserInfo);
    }
}
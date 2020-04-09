package com.jellyfishmix.wxinterchange.mapper;

import com.jellyfishmix.wxinterchange.entity.UserInfo;
import com.jellyfishmix.wxinterchange.utils.UniqueKeyUtil;
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
class UserInfoMapperTest {
    @Resource
    private UserInfoMapper userInfoMapper;

    @Test
    void insertUserInfo() {
        UserInfo userInfo = new UserInfo();
        userInfo.setUid(UniqueKeyUtil.getUniqueKey());
        userInfo.setUsername("test_username");
        userInfo.setOpenid("test_openid-4");
        int result = userInfoMapper.insertUserInfo(userInfo);
        assertEquals(1, result);
    }

    @Test
    void updateUserInfo() {
        UserInfo userInfo = new UserInfo();
        userInfo.setUid("158628518796132710");
        userInfo.setUsername("test-7");
        int result = userInfoMapper.updateUserInfo(userInfo);
        assertEquals(1, result);
    }

    @Test
    void selectUserInfoByUid() {
        String uid = "158628505011784344";
        UserInfo resultUserInfo = userInfoMapper.selectUserInfoByUid(uid);
        assertNotNull(resultUserInfo);
    }

    @Test
    void selectUserInfoByOpenid() {
        String openid = "test_openid-1";
        UserInfo resultUserInfo = userInfoMapper.selectUserInfoByOpenid(openid);
        assertNotNull(resultUserInfo);
    }
}
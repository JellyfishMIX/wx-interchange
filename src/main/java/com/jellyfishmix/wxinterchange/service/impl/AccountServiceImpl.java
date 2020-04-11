package com.jellyfishmix.wxinterchange.service.impl;

import com.jellyfishmix.wxinterchange.dto.UserInfoDTO;
import com.jellyfishmix.wxinterchange.entity.UserInfo;
import com.jellyfishmix.wxinterchange.service.AccountService;
import com.jellyfishmix.wxinterchange.service.UserInfoService;
import com.jellyfishmix.wxinterchange.utils.UniqueKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author JellyfishMIX
 * @date 2020/4/9 2:52 下午
 */
@Service("accountService")
public class AccountServiceImpl implements AccountService {
    @Autowired
    private UserInfoService userInfoService;

    /**
     * 注册账户
     * @param userInfo
     * @return
     */
    @Override
    public UserInfoDTO register(UserInfo userInfo) {
        String uid = UniqueKeyUtil.getUniqueKey();
        userInfo.setUid(uid);
        userInfoService.insertUserInfo(userInfo);
        UserInfoDTO userInfoDTO = userInfoService.queryByOpenid(userInfo.getOpenid());
        return userInfoDTO;
    }
}

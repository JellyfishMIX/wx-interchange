package com.jellyfishmix.wxinterchange.service;

import com.jellyfishmix.wxinterchange.dto.UserInfoDTO;
import com.jellyfishmix.wxinterchange.entity.UserInfo;

/**
 * @author JellyfishMIX
 * @date 2020/4/9 12:45 下午
 */
public interface AccountService {
    /**
     * 注册账户
     * @param userInfo
     * @return
     */
    UserInfoDTO register(UserInfo userInfo);
}

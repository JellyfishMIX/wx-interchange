# wx-interchange
This is a spring boot project that provides services for wechat group sharing.

## 亮点

1. 生成不重复的11位字符串

    com.jellyfishmix.wxinterchange.utils.UniqueKeyUtil

    使用Unix时间戳，十进制转十六进制
    
2. stateCode-Enum交换Util

    com.jellyfishmix.wxinterchange.enums.StateCodeEnum;
    
    传入一个stateCode，返回一个Enum

3. 解决文件上传时，高并发下可能出现的死锁问题

package com.jellyfishmix.wxinterchange.utils;

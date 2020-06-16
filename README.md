# wx-interchange
This is a spring boot project that provides services for wechat group sharing.

## 亮点

1. 生成不重复的11位字符串

    com.jellyfishmix.wxinterchange.utils/UniqueKeyUtil;

    使用Unix时间戳，十进制转十六进制
    
2. stateCode-Enum交换Util

    com.jellyfishmix.wxinterchange.enums/StateCodeEnum;
    
    传入一个stateCode，返回一个Enum

3. 解决文件上传时，高并发下可能出现的死锁问题

    package com.jellyfishmix.wxinterchange.service/RedisLockService;

4. 引入作业调度框架quartz处理定时任务，每天定时进行统计数据

    package com.jellyfishmix.wxinterchange.quartz/SpringJobFactory;
    
5. 使用redis的Sorted Set进行搜索热词统计(解析: [redis 实现搜索热词统计](https://juejin.im/post/5ed736dce51d45784f800dda))
    
    package com.jellyfishmix.wxinterchange.service.impl/SearchStatisticsServiceImpl
    
    public void zincrby(String sortedSetName, String value);
    
    public Set<ZSetOperations.TypedTuple<String>> queryTopSearchHotKey(String sortedSetName, Integer start, Integer end);

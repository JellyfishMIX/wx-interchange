package com.jellyfishmix.wxinterchange.controller;

import com.jellyfishmix.wxinterchange.enums.ScheduledTaskEnum;
import com.jellyfishmix.wxinterchange.service.ScheduledTaskService;
import com.jellyfishmix.wxinterchange.utils.ResultVOUtil;
import com.jellyfishmix.wxinterchange.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author JellyfishMIX
 * @date 2020/5/28 12:07 上午
 */
@RestController
@RequestMapping("/scheduled_task")
public class ScheduledTaskController {
    @Autowired
    private ScheduledTaskService scheduledTaskService;

    /**
     * 启动定时任务系统
     *
     * @return
     */
    @PostMapping("/start")
    public ResultVO start() {
        scheduledTaskService.start();
        return ResultVOUtil.success(ScheduledTaskEnum.SUCCESS.getStateCode(), ScheduledTaskEnum.SUCCESS.getStateMsg());
    }

    /**
     * 关闭定时任务系统
     *
     * @return
     */
    @PostMapping("/shutdown")
    public ResultVO shutdown() {
        scheduledTaskService.shutdown();
        return ResultVOUtil.success(ScheduledTaskEnum.SUCCESS.getStateCode(), ScheduledTaskEnum.SUCCESS.getStateMsg());
    }
}

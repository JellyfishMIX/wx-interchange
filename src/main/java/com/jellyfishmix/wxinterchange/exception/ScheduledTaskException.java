package com.jellyfishmix.wxinterchange.exception;

import com.jellyfishmix.wxinterchange.enums.ScheduledTaskEnum;

/**
 * @author JellyfishMIX
 * @date 2020/5/28 3:09 上午
 */
public class ScheduledTaskException extends RuntimeException {
    private static final long serialVersionUID = 6200276114902916295L;

    private Integer stateCode;
    private String stateMsg;

    public ScheduledTaskException(ScheduledTaskEnum scheduledTaskEnum) {
        this.stateCode = scheduledTaskEnum.getStateCode();
        this.stateMsg = scheduledTaskEnum.getStateMsg();
    }

    public ScheduledTaskException(ScheduledTaskEnum scheduledTaskEnum, String exceptionMessage) {
        super(exceptionMessage);
        this.stateCode = scheduledTaskEnum.getStateCode();
        this.stateMsg = scheduledTaskEnum.getStateMsg();
    }
}

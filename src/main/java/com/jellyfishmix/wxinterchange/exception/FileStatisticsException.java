package com.jellyfishmix.wxinterchange.exception;

import com.jellyfishmix.wxinterchange.enums.FileStatisticsEnum;
import lombok.Getter;

/**
 * @author JellyfishMIX
 * @date 2020/5/26 8:57 上午
 */
@Getter
public class FileStatisticsException extends RuntimeException {
    private static final long serialVersionUID = 7098709888151959164L;

    private Integer stateCode;
    private String stateMsg;

    public FileStatisticsException(FileStatisticsEnum fileStatisticsEnum) {
        super(fileStatisticsEnum.getStateMsg());
        this.stateCode = fileStatisticsEnum.getStateCode();
        this.stateMsg = fileStatisticsEnum.getStateMsg();
    }
}

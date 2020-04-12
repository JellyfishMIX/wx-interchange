package com.jellyfishmix.wxinterchange.vo;

import lombok.Data;

/**
 * @author JellyfishMIX
 * @date 2020/4/8 9:25 下午
 * 返回给前端的VO
 */
@Data
public class ResultVO<T> {
    /**
     * 表示请求是否成功
     */
    private Boolean success;
    private Integer stateCode;
    private String stateMsg;

    /**
     * 具体内容
     */
    private T data;
}

package com.jellyfishmix.wxinterchange.vo;

import lombok.Data;

/**
 * @author JellyfishMIX
 * @date 2020/4/6 2:37 上午
 */
@Data
public class WxMaAuthVO<T> {
    /**
     * response状况，请求成功/失败。0成功，1失败
     */
    private Integer status;

    /**
     * 具体内容
     */
    private T data;

    @Override
    public String toString() {
        return "WxMaAuthVO{" +
                "status=" + status +
                ", data=" + data +
                '}';
    }
}

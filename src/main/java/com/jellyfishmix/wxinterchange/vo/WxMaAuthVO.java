package com.jellyfishmix.wxinterchange.vo;

import lombok.Data;

/**
 * @author JellyfishMIX
 * @date 2020/4/6 2:37 上午
 */
@Data
public class WxMaAuthVO<T> {
    /**
     * response状况，请求成功/失败
     */
    private boolean success;

    /**
     * 具体内容
     */
    private T data;

    @Override
    public String toString() {
        return "WxMaAuthVO{" +
                "status=" + success +
                ", data=" + data +
                '}';
    }
}

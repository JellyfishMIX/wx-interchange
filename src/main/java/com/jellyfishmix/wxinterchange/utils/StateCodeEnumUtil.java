package com.jellyfishmix.wxinterchange.utils;

import com.jellyfishmix.wxinterchange.enums.StateCodeEnum;

/**
 * @author JellyfishMIX
 * @date 2020/4/6 4:13 上午
 * 用于stateCode-Enum交换
 */
public class StateCodeEnumUtil {
    public static <T extends StateCodeEnum> T getByStateCode(Integer stateCode, Class<T> enumClass) {
        for (T each : enumClass.getEnumConstants()) {
            if (stateCode.equals(each.getStateCode())) {
                return each;
            }
        }
        return null;
    }
}

package com.jellyfishmix.wxinterchange.utils;

/**
 * @author JellyfishMIX
 * @date 2020/4/20 12:44 下午
 */
public class PageCalculatorUtil {
    public static int calculatorRowIndex(int pageIndex, int pageSize) {
        if (pageIndex > 0) {
            return (pageIndex - 1) * pageSize;
        } else {
            return 0;
        }
    }
}

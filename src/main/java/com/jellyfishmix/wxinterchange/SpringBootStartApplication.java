package com.jellyfishmix.wxinterchange;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author JellyfishMIX
 * @date 2020/4/9 6:16 下午
 */
public class SpringBootStartApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        // 指向原先用main方法执行的Application启动类
        return builder.sources(WxInterchangeApplication.class);
    }
}
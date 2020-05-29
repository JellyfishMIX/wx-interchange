package com.jellyfishmix.wxinterchange.config;

import com.jellyfishmix.wxinterchange.quartz.SpringJobFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * @author JellyfishMIX
 * @date 2020/5/30 1:21 上午
 */
@Configuration
public class QuartzSchedulerConfig {
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        // 使用自定义的quartzJob 工厂类，避免出现Job类不能注入的问题
        schedulerFactoryBean.setJobFactory(getSpringJobFactory());
        // 定时任务开始启动后延迟5秒开始
        schedulerFactoryBean.setStartupDelay(5);
        return schedulerFactoryBean;
    }

    @Bean
    public SpringJobFactory getSpringJobFactory() {
        return new SpringJobFactory();
    }
}

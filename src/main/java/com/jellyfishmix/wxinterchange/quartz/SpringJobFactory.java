package com.jellyfishmix.wxinterchange.quartz;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
import org.springframework.stereotype.Component;

/**
 * 自动注入所需工厂类
 *
 * @author JellyfishMIX
 * @date 2020/5/30 1:00 上午
 */
@Component
public class SpringJobFactory extends AdaptableJobFactory {
    @Autowired
    private AutowireCapableBeanFactory autowireCapableBeanFactory;

    @Override
    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
        // 调用父类的方法
        Object jobInstance = super.createJobInstance(bundle);
        // 进行注入,这属于Spring的技术,不清楚的可以查看Spring的API.
        autowireCapableBeanFactory.autowireBean(jobInstance);
        return jobInstance;
    }
}

package ruan.cong.summerframework.test.post.processor;

import ruan.cong.summerframework.beans.factory.config.BeanPostProcessor;

//todo 实现@Value注解
public class ValueBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        // 1、扫描配置文件

        // 2、扫描直接字符串

        // 3、属性注入

        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        return null;
    }
}

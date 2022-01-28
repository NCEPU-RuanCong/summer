package ruan.cong.summerframework.test.post.processor;

import ruan.cong.summerframework.beans.factory.config.BeanPostProcessor;

public class MyPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
//        System.out.println("MyPostProcessor--【BeanPostProcessor调用】--初始化前置处理器");
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
//        System.out.println("MyPostProcessor--【BeanPostProcessor调用】--初始化后置处理器");
        return bean;
    }
}

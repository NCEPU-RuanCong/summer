package ruan.cong.summerframework.test.post.processor;

import ruan.cong.summerframework.beans.factory.context.BeanPostProcessor;

public class MyBeanPostProcessor implements BeanPostProcessor {
    @Override
    public void beforeBeanPostProcessor() {
        System.out.println("【Bean初始化前调用--MyBeanPostProcessor--before】");
    }

    @Override
    public void afterBeanPostProcessor() {
        System.out.println("【Bean初始化后调用--MyBeanPostProcessor--after】");
    }
}

package ruan.cong.summerframework.test.post.processor;

import ruan.cong.summerframework.beans.factory.context.BeanFactoryPostProcessor;

public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void afterBeanFactoryPostProcessor() {
        System.out.println("【BeanFactoryPostProcessor】--调用");
    }
}

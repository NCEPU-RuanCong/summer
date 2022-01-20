package ruan.cong.summerframework.test.post.processor;

import ruan.cong.summerframework.beans.factory.ConfigurableListableBeanFactory;
import ruan.cong.summerframework.beans.factory.config.BeanFactoryPostProcessor;

public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        System.out.println("【BeanFactory后置处理器调用】---MyBeanFactoryPostProcessor");
    }
}

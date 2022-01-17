package ruan.cong.summerframework.beans.factory.support;

import ruan.cong.summerframework.beans.factory.config.BeanDefinition;

public interface BeanDefinitionRegistry {
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);
}

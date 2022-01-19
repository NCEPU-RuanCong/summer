package ruan.cong.summerframework.beans.factory.support;

import ruan.cong.summerframework.beans.BeanException;
import ruan.cong.summerframework.beans.factory.config.BeanDefinition;

public interface BeanDefinitionRegistry {
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

    BeanDefinition getBeanDefinition(String beanName) throws BeanException;

    boolean containsBeanDefinition(String beanName) throws BeanException;

    String[] getBeanDefinitionNames();
}

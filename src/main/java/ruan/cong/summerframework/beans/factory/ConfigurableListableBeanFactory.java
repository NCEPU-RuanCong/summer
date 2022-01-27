package ruan.cong.summerframework.beans.factory;

import ruan.cong.summerframework.beans.BeanException;
import ruan.cong.summerframework.beans.factory.config.AutowireCapableBeanFactory;
import ruan.cong.summerframework.beans.factory.config.BeanDefinition;
import ruan.cong.summerframework.beans.factory.config.BeanPostProcessor;
import ruan.cong.summerframework.beans.factory.config.ConfigurableBeanFactory;

public interface ConfigurableListableBeanFactory extends ListableBeanFactory, ConfigurableBeanFactory, AutowireCapableBeanFactory {

    BeanDefinition getBeanDefinition(String beanName) throws BeanException;

    void preInstantiateSingletons() throws BeanException;

    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);
}

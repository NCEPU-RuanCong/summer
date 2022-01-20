package ruan.cong.summerframework.beans.factory.config;

import ruan.cong.summerframework.beans.BeanException;
import ruan.cong.summerframework.beans.factory.BeanFactory;

public interface AutowireCapableBeanFactory extends BeanFactory {
    Object applyBeanPostProcessorBeforeInitialization(Object bean, String beanName) throws BeanException;

    Object applyBeanPostProcessorAfterInitialization(Object bean, String beanName) throws BeanException;
}

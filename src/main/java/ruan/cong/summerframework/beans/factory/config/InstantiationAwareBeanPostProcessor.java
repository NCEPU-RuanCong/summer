package ruan.cong.summerframework.beans.factory.config;

import ruan.cong.summerframework.beans.BeanException;
import ruan.cong.summerframework.beans.PropertyValues;

/**
 *
 * 这块的前后置处理器有点多呀，真的有点坑！
 *
 */
public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor{
    Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeanException;

    PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName) throws BeanException;

    default Object getEarlyBeanReference(Object bean, String beanName) {
        return bean;
    }

    boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeanException;
}

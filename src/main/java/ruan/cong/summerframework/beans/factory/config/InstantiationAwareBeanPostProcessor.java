package ruan.cong.summerframework.beans.factory.config;

import ruan.cong.summerframework.beans.BeanException;
import ruan.cong.summerframework.beans.PropertyValues;

public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor{
    Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeanException;

    PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName) throws BeanException;
}

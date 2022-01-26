package ruan.cong.summerframework.beans.factory.config;

import ruan.cong.summerframework.beans.BeanException;

public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor{
    Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeanException;
}

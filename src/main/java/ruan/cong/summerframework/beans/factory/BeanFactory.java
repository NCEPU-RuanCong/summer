package ruan.cong.summerframework.beans.factory;

import ruan.cong.summerframework.beans.BeanException;

public interface BeanFactory {
    Object getBean(String beanName);

    Object getBean(String beanName, Object... args);

    <T> T getBean(String name, Class<T> requiredType) throws BeanException;

    <T> T getBean(Class<T> requiredType) throws BeanException;
}

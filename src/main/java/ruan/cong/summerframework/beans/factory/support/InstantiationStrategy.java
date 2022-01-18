package ruan.cong.summerframework.beans.factory.support;

import java.lang.reflect.Constructor;
import ruan.cong.summerframework.beans.BeanException;
import ruan.cong.summerframework.beans.factory.config.BeanDefinition;

public interface InstantiationStrategy {
    Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor ctor, Object[] args) throws BeanException;
}

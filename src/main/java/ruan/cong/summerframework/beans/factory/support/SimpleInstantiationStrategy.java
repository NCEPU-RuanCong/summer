package ruan.cong.summerframework.beans.factory.support;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import ruan.cong.summerframework.beans.BeanException;
import ruan.cong.summerframework.beans.factory.config.BeanDefinition;

public class SimpleInstantiationStrategy implements InstantiationStrategy{
    @Override
    public Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor ctor, Object[] args) throws BeanException{
        Class<?> clazz = beanDefinition.getBeanClass();
        try {
            if (ctor == null) {
                return clazz.newInstance();
            }
            return clazz.getDeclaredConstructor(ctor.getParameterTypes()).newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | java.lang.NoSuchMethodException e) {
            throw new BeanException("bean instance failed!", e);
        }
    }
}

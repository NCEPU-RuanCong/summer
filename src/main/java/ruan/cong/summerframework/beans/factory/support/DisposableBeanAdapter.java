package ruan.cong.summerframework.beans.factory.support;

import java.lang.reflect.Method;
import ruan.cong.summerframework.beans.BeanException;
import ruan.cong.summerframework.beans.factory.DisposableBean;
import ruan.cong.summerframework.beans.factory.config.BeanDefinition;
import ruan.cong.summerframework.utils.StringUtils;

public class DisposableBeanAdapter implements DisposableBean {
    private final Object bean;
    private final String beanName;
    private String destroyMethodName;

    public DisposableBeanAdapter(Object bean, String beanName, BeanDefinition beanDefinition) {
        this.bean = bean;
        this.beanName = beanName;
        this.destroyMethodName = beanDefinition.getDestroyMethodName();
    }

    @Override
    public void destroy() throws Exception {
        if(bean instanceof DisposableBean){
            ((DisposableBean)bean).destroy();
        }

        if(StringUtils.nonEmpty(destroyMethodName) && !(bean instanceof DisposableBean && "destroy".equals(this.destroyMethodName))){
            Method destroyMethod = bean.getClass().getMethod(destroyMethodName);
            if(null == destroyMethod){
                throw new BeanException("destry method :" + destroyMethodName + " not found!");
            }
            destroyMethod.setAccessible(true);
            destroyMethod.invoke(bean);
        }
    }
}

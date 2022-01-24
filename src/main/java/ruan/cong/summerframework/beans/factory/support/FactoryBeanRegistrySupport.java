package ruan.cong.summerframework.beans.factory.support;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import ruan.cong.summerframework.beans.BeanException;
import ruan.cong.summerframework.beans.factory.FactoryBean;

public abstract class FactoryBeanRegistrySupport extends DefaultSingletonBeanRegistry{

    private final Map<String, Object> factoryBeanObjectCache = new ConcurrentHashMap<>();

    protected Object getCachedObjectFromFactoryBean(String beanName){
        Object object = factoryBeanObjectCache.get(beanName);
        return (NULL_OBJECT != object ? object : null);
    }

    protected Object getObjectFromFactoryBean(FactoryBean factoryBean, String beanName){
        if(factoryBean.isSingleton()){
            Object object = factoryBeanObjectCache.get(beanName);
            if(null == object){
                object = doGetObjectFromFactoryBean(factoryBean, beanName);
                factoryBeanObjectCache.put(beanName, (null == object ? NULL_OBJECT : object));
            }
            return object;
        } else {
            return doGetObjectFromFactoryBean(factoryBean, beanName);
        }
    }

    /**
     *
     *
     * 等着别人来继承的接口，只需要实现FactoryBean接口的关键函数即可融入Bean的生命周期
     * @param factoryBean
     * @param beanName
     * @return
     */
    protected Object doGetObjectFromFactoryBean(FactoryBean factoryBean, String beanName){
        try {
            return factoryBean.getObject();
        } catch (Exception e){
            throw new BeanException("create bean use factory Bean failed, beanName is " + beanName);
        }
    }
}

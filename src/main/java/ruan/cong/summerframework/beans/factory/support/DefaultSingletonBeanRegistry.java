package ruan.cong.summerframework.beans.factory.support;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import ruan.cong.summerframework.beans.factory.config.SingletonBeanRegistry;
import ruan.cong.summerframework.beans.factory.exception.BeanNotFoundException;
import ruan.cong.summerframework.utils.StringUtils;

public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {
    private Map<String, Object> singletonObjects = new ConcurrentHashMap<String, Object>();

    @Override
    public Object getSingletonBean(String beanName) {
        return singletonObjects.get(beanName);
    }

    public void addSingletonBean(String beanName, Object singletonObject){
        if(StringUtils.isEmpty(beanName)){
            throw new BeanNotFoundException(beanName + " not found!");
        }
        singletonObjects.put(beanName, singletonObject);
    }
}

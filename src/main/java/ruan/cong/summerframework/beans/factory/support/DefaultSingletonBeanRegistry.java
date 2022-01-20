package ruan.cong.summerframework.beans.factory.support;

import java.util.HashMap;
import java.util.Map;
import ruan.cong.summerframework.beans.factory.config.SingletonBeanRegistry;
import ruan.cong.summerframework.beans.factory.exception.BeanNotFoundException;
import ruan.cong.summerframework.utils.StringUtils;

public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {
    private Map<String, Object> singletonObjects = new HashMap();

    public Map<String, Object> getSingletonObjects() {
        return singletonObjects;
    }

    public void setSingletonObjects(Map<String, Object> singletonObjects) {
        this.singletonObjects = singletonObjects;
    }

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

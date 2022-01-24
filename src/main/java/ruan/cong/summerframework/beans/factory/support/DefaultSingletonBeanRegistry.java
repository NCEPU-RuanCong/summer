package ruan.cong.summerframework.beans.factory.support;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import ruan.cong.summerframework.beans.BeanException;
import ruan.cong.summerframework.beans.factory.DisposableBean;
import ruan.cong.summerframework.beans.factory.config.SingletonBeanRegistry;
import ruan.cong.summerframework.beans.factory.exception.BeanNotFoundException;
import ruan.cong.summerframework.utils.StringUtils;

public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    protected static final Object NULL_OBJECT = new Object();

    private Map<String, Object> singletonObjects = new HashMap();

    private Map<String, DisposableBean> disposableBeans = new HashMap<>();

    public Object getSingletonBean(String beanName) {
        return singletonObjects.get(beanName);
    }

    public void registerDisposalBean(String beanName, DisposableBean bean){
        disposableBeans.put(beanName, bean);
    }

    public void addSingletonBean(String beanName, Object singletonObject){
        if(StringUtils.isEmpty(beanName)){
            throw new BeanNotFoundException(beanName + " not found!");
        }
        singletonObjects.put(beanName, singletonObject);
    }

    public void destroySingletons(){
        Set<String> keySet = disposableBeans.keySet();
        Object[] destroyBeanNames = keySet.toArray();
        for(int i = 0; i < destroyBeanNames.length; i++){
            Object beanName = destroyBeanNames[i];
            DisposableBean disposableBean = disposableBeans.remove(beanName);
            try {
                disposableBean.destroy();
            } catch (Exception e){
                throw new BeanException("destroy method failed! " + beanName + "execute failed!");
            }
        }
    }
}

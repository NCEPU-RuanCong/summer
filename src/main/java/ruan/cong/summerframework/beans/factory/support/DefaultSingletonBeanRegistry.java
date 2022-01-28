package ruan.cong.summerframework.beans.factory.support;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import ruan.cong.summerframework.beans.BeanException;
import ruan.cong.summerframework.beans.factory.DisposableBean;
import ruan.cong.summerframework.beans.factory.ObjectFactory;
import ruan.cong.summerframework.beans.factory.config.SingletonBeanRegistry;
import ruan.cong.summerframework.beans.factory.exception.BeanNotFoundException;
import ruan.cong.summerframework.utils.StringUtils;

public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    protected static final Object NULL_OBJECT = new Object();

    // 一级缓存，存放普通Bean
    private Map<String, Object> singletonObjects = new ConcurrentHashMap<>();

    // 二级缓存，存放半成品Bean, 提前暴露对象
    protected final Map<String, Object> earlySingletonObjects = new HashMap<>();

    // 三级缓存，存放代理对象
    private final Map<String, ObjectFactory<?>> singletonFactories = new HashMap<String, ObjectFactory<?>>();

    private Map<String, DisposableBean> disposableBeans = new HashMap<>();


    public Object getSingletonBean(String beanName) {
        Object singletonObject =  singletonObjects.get(beanName);
        if (null == singletonObject) {
            singletonObject = earlySingletonObjects.get(beanName);
            if (null == singletonObject) {
                ObjectFactory<?> singletonFactory = singletonFactories.get(beanName);
                if (null != singletonFactory) {
                    singletonObject = singletonFactory.getObject();
                    earlySingletonObjects.put(beanName, singletonObject);
                    singletonFactories.remove(beanName);
                }
            }
        }
        return singletonObject;
    }

    public void registerSingleton(String beanName, Object singletonObject){
        singletonObjects.put(beanName, singletonObject);
        earlySingletonObjects.remove(beanName);
        singletonFactories.remove(beanName);
    }

    protected void addSingletonFactory(String beanName, ObjectFactory<?> singletonFactory) {
        if (!this.singletonObjects.containsKey(beanName)) {
            this.singletonFactories.put(beanName, singletonFactory);
            this.earlySingletonObjects.remove(beanName);
        }
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

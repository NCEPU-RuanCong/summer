package ruan.cong.summerframework.beans.factory.config;

import ruan.cong.summerframework.beans.PropertyValues;
import ruan.cong.summerframework.utils.StringUtils;

public class BeanDefinition {
    private Class beanClass;

    private PropertyValues propertyValues;

    private String initMethodName;

    private String destroyMethodName;

    String SCOPE_SINGLETON = ConfigurableBeanFactory.SCOPE_SINGLETON;
    String SCOPE_PROTOTYPE = ConfigurableBeanFactory.SCOPE_PROTOTYPE;

    private boolean singleton;
    private boolean prototype;

    public boolean isSingleton() {
        return singleton;
    }

    public boolean isPrototype() {
        return prototype;
    }

    public void setScope(String scope){
        if(StringUtils.isEmpty(scope)){
            singleton = true;
            prototype = false;
            return;
        }
        singleton = SCOPE_SINGLETON.equals(scope);
        prototype = SCOPE_PROTOTYPE.equals(scope);
    }

    public String getDestroyMethodName() {
        return destroyMethodName;
    }

    public void setDestroyMethodName(String destroyMethodName) {
        this.destroyMethodName = destroyMethodName;
    }

    public String getInitMethodName() {
        return initMethodName;
    }

    public void setInitMethodName(String initMethodName) {
        this.initMethodName = initMethodName;
    }

    public BeanDefinition(Class beanClass, PropertyValues propertyValues) {
        this.beanClass = beanClass;
        this.propertyValues = propertyValues;
    }

    public BeanDefinition(Class beanClass){
        this.beanClass = beanClass;
        propertyValues = new PropertyValues();
    }

    public Class getBeanClass() {
        return beanClass;
    }

    public PropertyValues getPropertyValues() {
        return propertyValues;
    }

    public void setPropertyValues(PropertyValues propertyValues) {
        this.propertyValues = propertyValues;
    }
}

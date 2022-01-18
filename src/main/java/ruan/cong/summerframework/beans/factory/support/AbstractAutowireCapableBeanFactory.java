package ruan.cong.summerframework.beans.factory.support;

import java.lang.reflect.Constructor;
import java.util.Properties;
import ruan.cong.summerframework.beans.BeanException;
import ruan.cong.summerframework.beans.PropertyValue;
import ruan.cong.summerframework.beans.PropertyValues;
import ruan.cong.summerframework.beans.factory.config.BeanDefinition;
import ruan.cong.summerframework.beans.factory.config.BeanReference;
import ruan.cong.summerframework.utils.BeanUtils;

/**
 *
 * 最核心的就是createBean，从这个类开始真正具备了Bean从无到有的功能
 *
 */
public class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory{

    private InstantiationStrategy instantiationStrategy = new CglibSubclassingInstantiationStrategy();

    public InstantiationStrategy getInstantiationStrategy() {
        return instantiationStrategy;
    }

    public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }

    @Override
    protected BeanDefinition getBeanDefinition(String beanName) throws BeanException {
        return null;
    }

    protected Object createBeanInstance(BeanDefinition beanDefinition, String beanName, Object[] args){
        Class<?> clazz = beanDefinition.getBeanClass();
        Constructor ctor = null;
        for(Constructor constructor : clazz.getDeclaredConstructors()){
            // 这里没有判断参数的类型，是一个bug，应该长度和参数的类型都对应上
            if(null != constructor && constructor.getParameterTypes().length == args.length){
                ctor = constructor;
                break;
            }
        }
        Object obj = getInstantiationStrategy().instantiate(beanDefinition, beanName, ctor, args);
        applyPropertyValues(beanName, beanDefinition, obj);
        return obj;
    }

    @Override
    protected Object createBean(String beanName) throws BeanException {
        Object obj = null;
        try {
            BeanDefinition beanDefinition = getBeanDefinition(beanName);
            obj = beanDefinition.getBeanClass().newInstance();
            applyPropertyValues(beanName, beanDefinition, obj);
        } catch (InstantiationException | IllegalAccessException e){
            throw new BeanException("bean " + beanName + " create failed!", e);
        }
        addSingletonBean(beanName, obj);
        return obj;
    }

    @Override
    protected Object createBean(BeanDefinition beanDefinition, String beanName, Object[] args) throws BeanException{
        Object obj = null;
        obj = createBeanInstance(beanDefinition, beanName, args);
        addSingletonBean(beanName, obj);
        return obj;
    }

    protected void applyPropertyValues(String beanName, BeanDefinition beanDefinition, Object bean){
        PropertyValues propertyValues = beanDefinition.getPropertyValues();
        if(null == propertyValues){
            return;
        }
        for(PropertyValue pv : propertyValues.getPropertyValues()){
            Object value = pv.getValue();
            if (value instanceof BeanReference) {
                BeanReference beanReference = (BeanReference)value;
                value = getBean(beanReference.getBeanName());
            }
            BeanUtils.setFieldValue(bean, pv.getName(), value);
        }
    }
}

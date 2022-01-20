package ruan.cong.summerframework.beans.factory.support;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;
import ruan.cong.summerframework.beans.BeanException;
import ruan.cong.summerframework.beans.PropertyValue;
import ruan.cong.summerframework.beans.PropertyValues;
import ruan.cong.summerframework.beans.factory.InitializingBean;
import ruan.cong.summerframework.beans.factory.config.AutowireCapableBeanFactory;
import ruan.cong.summerframework.beans.factory.config.BeanDefinition;
import ruan.cong.summerframework.beans.factory.config.BeanPostProcessor;
import ruan.cong.summerframework.beans.factory.config.BeanReference;
import ruan.cong.summerframework.utils.BeanUtils;
import ruan.cong.summerframework.utils.StringUtils;

/**
 *
 * 最核心的就是createBean，从这个类开始真正具备了Bean从无到有的功能
 *
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {

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
        obj = initializeBean(beanName, obj, beanDefinition);
        return obj;
    }

    @Override
    protected Object createBean(String beanName) throws BeanException {
        Object obj = null;
        try {
            BeanDefinition beanDefinition = getBeanDefinition(beanName);
            obj = beanDefinition.getBeanClass().newInstance();
            applyPropertyValues(beanName, beanDefinition, obj);
            obj = initializeBean(beanName, obj, beanDefinition);
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

    private Object initializeBean(String beanName, Object obj, BeanDefinition beanDefinition){

        // 1、前置处理器
        Object wrapperBean = applyBeanPostProcessorBeforeInitialization(obj, beanName);

        // 2、方法调用
        invokeInitMethod(beanName, obj, beanDefinition);

        // 3、后置处理器
        wrapperBean = applyBeanPostProcessorAfterInitialization(wrapperBean, beanName);

        return wrapperBean;
    }

    private void invokeInitMethod(String beanName, Object bean, BeanDefinition beanDefinition){
        // 1、接口实现
        if(bean instanceof InitializingBean){
            ((InitializingBean) bean).afterPropertiesSet();
        }

        // 2、配置文件
        String initMethodName = beanDefinition.getInitMethodName();
        if(StringUtils.nonEmpty(initMethodName) && !(bean instanceof InitializingBean)){
            try {
                Method initMethod = beanDefinition.getBeanClass().getMethod(initMethodName);
                initMethod.invoke(bean);
            } catch (Exception e){
                throw new BeanException("no such initMethod:" + initMethodName + "on bean:" + beanName);
            }
        }
    }

    /**
     *
     * 看了这个的实现才知道，这个BeanPostProcessor是让每个Bean都经过所有的BeanPostProcessor，
     * 假定有N个检查关卡，这个关卡就是BeanPostProcessor，有M个人等待检查，这M个人就是Bean
     *
     * 这里还有个问题，如果我的BeanPostProcessor处理完成后返回null那么不就崩了？
     * @param bean
     * @param beanName
     * @return
     */
    @Override
    public Object applyBeanPostProcessorBeforeInitialization(Object bean, String beanName)throws BeanException{
        Object currentBean = bean;
        List<BeanPostProcessor> beanPostProcessors = getBeanPostProcessors();
        for(BeanPostProcessor beanPostProcessor : beanPostProcessors){
            Object result = beanPostProcessor.postProcessBeforeInitialization(currentBean, beanName);
            if(result == null) return currentBean;
            currentBean = result;
        }
        return currentBean;
    }

    @Override
    public Object applyBeanPostProcessorAfterInitialization(Object bean, String beanName) throws BeanException{
        Object currentBean = bean;
        List<BeanPostProcessor> beanPostProcessors = getBeanPostProcessors();
        for(BeanPostProcessor beanPostProcessor : beanPostProcessors){
            Object result = beanPostProcessor.postProcessAfterInitialization(currentBean, beanName);
            if(result == null) return currentBean;
            currentBean = result;
        }
        return currentBean;
    }
}

package ruan.cong.summerframework.beans.factory.annotation;

import java.lang.reflect.Field;
import ruan.cong.summerframework.beans.BeanException;
import ruan.cong.summerframework.beans.PropertyValues;
import ruan.cong.summerframework.beans.factory.BeanFactory;
import ruan.cong.summerframework.beans.factory.BeanFactoryAware;
import ruan.cong.summerframework.beans.factory.ConfigurableListableBeanFactory;
import ruan.cong.summerframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import ruan.cong.summerframework.utils.BeanUtils;
import ruan.cong.summerframework.utils.ClassUtils;

public class AutowiredAnnotationBeanPostProcessor implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {

    private ConfigurableListableBeanFactory configurableListableBeanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeanException {
        this.configurableListableBeanFactory = (ConfigurableListableBeanFactory)beanFactory;
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeanException {
        return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeanException {
        return true;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        return bean;
    }

    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName) throws BeanException{
        Class<?> clazz = bean.getClass();
        clazz = ClassUtils.isCglibProxyClass(clazz) ? clazz.getSuperclass() : clazz;

        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            Value valueAnnotation = field.getAnnotation(Value.class);
            if (null!=valueAnnotation) {
                String value = valueAnnotation.value();
                value = configurableListableBeanFactory.resolveEmbeddedValue(value);
                BeanUtils.setFieldValue(bean, field.getName(), value);
            }
        }

        for (Field field : declaredFields) {
            Autowired autowiredAnnotation = field.getAnnotation(Autowired.class);
            if (null != autowiredAnnotation) {
                Class<?> fieldType = field.getType();
                String dependencyName = null;
                Qualifier qualifierAnnotation = field.getAnnotation(Qualifier.class);
                Object dependencyBean = null;
                if (null != qualifierAnnotation) {
                    dependencyName = qualifierAnnotation.value();
                    dependencyBean = this.configurableListableBeanFactory.getBean(dependencyName);
                } else {
                    dependencyBean = this.configurableListableBeanFactory.getBean(fieldType);
                }
                BeanUtils.setFieldValue(bean, field.getName(), dependencyBean);
            }
        }

        return pvs;
    }

}

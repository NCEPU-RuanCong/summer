package ruan.cong.summerframework.beans.factory.support;

import java.util.ArrayList;
import java.util.List;
import ruan.cong.summerframework.beans.BeanException;
import ruan.cong.summerframework.beans.factory.BeanFactory;
import ruan.cong.summerframework.beans.factory.FactoryBean;
import ruan.cong.summerframework.beans.factory.config.BeanDefinition;
import ruan.cong.summerframework.beans.factory.config.BeanPostProcessor;
import ruan.cong.summerframework.utils.ClassUtils;

/**
 *
 * abstractBeanFactory已经持有了map（实际持有的是DefaultSingletonBeanRegistry的Bean Map）
 * 但是并没有持有BeanDefinition相关的map，因此不应该又addBeanDefinition函数
 * 因为持有Bean的map所以应该又getBean，addBean（createBean，因为我们不会显示的自己手动添加Bean）
 *
 *
 *
 * 但是为什么会有一个getBeanDefinition呢？？？
 *
 */
public abstract class AbstractBeanFactory extends FactoryBeanRegistrySupport implements BeanFactory {

    private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();

    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();

    public List<BeanPostProcessor> getBeanPostProcessors() {
        return beanPostProcessors;
    }

    public ClassLoader getBeanClassLoader() {
        return beanClassLoader;
    }

    @Override
    public Object getBean(String beanName, Object... args){
        return doGetBean(beanName, args);
    }

    @Override
    public Object getBean(String beanName){
        return doGetBean(beanName, null);
    }

    public <T> T getBean(String name, Class<T> requiredType) throws BeanException{
        return (T)getBean(name);
    }

    protected <T> T doGetBean(final String name, final Object[] args){
        // 1、获取单例Bean
        Object shareInstance = getSingletonBean(name);
        if(null != shareInstance){
            return (T)getObjectForBeanInstance(shareInstance, name);
        }

        BeanDefinition beanDefinition = getBeanDefinition(name);
        Object bean = createBean(beanDefinition, name, args);
        return (T)getObjectForBeanInstance(bean, name);
    }

    private Object getObjectForBeanInstance(Object beanInstance, String beanName){
        if(!(beanInstance instanceof FactoryBean)){
            return beanInstance;
        }

        Object object = getCachedObjectFromFactoryBean(beanName);
        if(null == object){
            FactoryBean<?> factoryBean = (FactoryBean<?>)beanInstance;
            object = getObjectFromFactoryBean(factoryBean, beanName);
        }

        return object;
    }

    protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeanException;

    protected abstract Object createBean(String beanName) throws BeanException;

    protected abstract Object createBean(BeanDefinition beanDefinition, String beanName, Object[] args) throws BeanException;

    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor){
        this.beanPostProcessors.remove(beanPostProcessor);
        this.beanPostProcessors.add(beanPostProcessor);
    }
}

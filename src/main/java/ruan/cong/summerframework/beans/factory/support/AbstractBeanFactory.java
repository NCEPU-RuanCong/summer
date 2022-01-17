package ruan.cong.summerframework.beans.factory.support;

import ruan.cong.summerframework.beans.BeanException;
import ruan.cong.summerframework.beans.factory.BeanFactory;
import ruan.cong.summerframework.beans.factory.config.BeanDefinition;
import ruan.cong.summerframework.beans.factory.exception.BeanDefinitionException;
import ruan.cong.summerframework.beans.factory.exception.BeanNotFoundException;
import ruan.cong.summerframework.utils.StringUtils;

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
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {

    @Override
    public Object getBean(String beanName){
        if(StringUtils.isEmpty(beanName)){
            throw new BeanNotFoundException(beanName + " not found");
        }
        Object obj = getSingletonBean(beanName);
        if(obj != null){
            return obj;
        }
        BeanDefinition beanDefinition =getBeanDefinition(beanName);
        if(beanDefinition == null){
            throw new BeanDefinitionException(beanName + " related beanDefinition not found!");
        }
        return createBean(beanName);
    }

    protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeanException;

    protected abstract Object createBean(String beanName) throws BeanException;
}

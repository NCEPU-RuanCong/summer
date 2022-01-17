package ruan.cong.summerframework.beans.factory.support;

import ruan.cong.summerframework.beans.BeanException;
import ruan.cong.summerframework.beans.factory.config.BeanDefinition;
import ruan.cong.summerframework.beans.factory.exception.BeanNotFoundException;
import ruan.cong.summerframework.utils.StringUtils;

/**
 *
 * 最核心的就是createBean，从这个类开始真正具备了Bean从无到有的功能
 *
 */
public class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory{

    @Override
    protected BeanDefinition getBeanDefinition(String beanName) throws BeanException {
        return null;
    }

    @Override
    protected Object createBean(String beanName) throws BeanException {
        if (StringUtils.isEmpty(beanName)) {
            throw new BeanNotFoundException("empty bean name!");
        }
        Object obj = getBean(beanName);
        if (obj != null) {
            return obj;
        }
        try {
            obj = getBeanDefinition(beanName).getBeanClass().newInstance();
        } catch (BeanException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        addSingletonBean(beanName, obj);
        return obj;
    }
}

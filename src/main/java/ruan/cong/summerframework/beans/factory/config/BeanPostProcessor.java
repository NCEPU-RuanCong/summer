package ruan.cong.summerframework.beans.factory.config;

import ruan.cong.summerframework.beans.factory.ConfigurableListableBeanFactory;

public interface BeanPostProcessor {
    /**
     *
     * 这个是操作Bean的，因此即传入了ConfigurableListableBeanFactory又传入了beanName
     * @param beanFactory
     * @param beanName
     * @return
     */
    Object postProcessBeforeInitialization(Object bean, String beanName);

    Object postProcessAfterInitialization(Object bean, String beanName);

}

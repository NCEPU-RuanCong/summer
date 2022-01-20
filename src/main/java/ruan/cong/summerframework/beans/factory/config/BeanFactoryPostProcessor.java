package ruan.cong.summerframework.beans.factory.config;

import ruan.cong.summerframework.beans.factory.ConfigurableListableBeanFactory;

public interface BeanFactoryPostProcessor {
    /**
     *
     * 这个类是针对BeanFactory的，所以只传入了ConfigurableListableBeanFactory，这样技能操作到beanFactory，又能操作到
     * 想要操作的Bean
     * @param beanFactory
     */
    void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory);
}

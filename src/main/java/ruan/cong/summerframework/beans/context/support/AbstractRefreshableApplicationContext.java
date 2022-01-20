package ruan.cong.summerframework.beans.context.support;

import ruan.cong.summerframework.beans.BeanException;
import ruan.cong.summerframework.beans.factory.ConfigurableListableBeanFactory;
import ruan.cong.summerframework.beans.factory.support.DefaultListableBeanFactory;

public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext{

    private DefaultListableBeanFactory defaultListableBeanFactory;

    @Override
    protected void refreshBeanFactory() throws BeanException {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        this.defaultListableBeanFactory = beanFactory;
        loadBeanDefinitions(beanFactory);
    }

    @Override
    public ConfigurableListableBeanFactory getBeanFactory(){
        return defaultListableBeanFactory;
    }

    /**
     *
     * 注意前面的DefaultListableBeanFactory持有的是3个根据Resource构建的方法
     * @param beanFactory
     */
    protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory){}

}

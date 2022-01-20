package ruan.cong.summerframework.beans.factory.context;

import ruan.cong.summerframework.beans.factory.support.DefaultListableBeanFactory;

public abstract class ConfigurableApplicationContext extends DefaultListableBeanFactory implements AbstractApplicationContext{
    ClassPathXMLApplicationContext refresh(){
        return null;
    }

    protected void createBeanFactory(){}

    protected ClassPathXMLApplicationContext getBeanFactory(){
        return null;
    }

    protected void invokeBeanFactoryPostProcessor(DefaultListableBeanFactory defaultListableBeanFactory){

    }

    protected void registryBeanPostProcessor(DefaultListableBeanFactory defaultListableBeanFactory){}

    protected void preInstantiateBean(){}
}

package ruan.cong.summerframework.beans.context.support;

import ruan.cong.summerframework.beans.factory.support.DefaultListableBeanFactory;
import ruan.cong.summerframework.beans.factory.xml.XMLBeanDefinitionReader;

public abstract class AbstractXmlApplicationContext extends AbstractRefreshableApplicationContext{

    @Override
    public void loadBeanDefinitions(DefaultListableBeanFactory beanFactory){
        XMLBeanDefinitionReader xmlBeanDefinitionReader = new XMLBeanDefinitionReader(beanFactory);
        String[] configLocations = getConfigLocations();
        if(null != configLocations) {
            xmlBeanDefinitionReader.loadBeanDefinitions(configLocations);
        }
    }

    protected abstract String[] getConfigLocations();
}

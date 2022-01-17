package ruan.cong.summerframework.beans.factory.support;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import ruan.cong.summerframework.beans.BeanException;
import ruan.cong.summerframework.beans.factory.config.BeanDefinition;
import ruan.cong.summerframework.utils.StringUtils;

public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry{

    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        if (StringUtils.isEmpty(beanName)) {
            throw new BeanException("empty bean name!");
        }
        beanDefinitionMap.put(beanName, beanDefinition);
    }

    public BeanDefinition getBeanDefinition(String beanName) {
        if (StringUtils.isEmpty(beanName)) {
            throw new BeanException("empty bean name!");
        }
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if(beanDefinition == null){
            throw new BeanException("no bean named " + beanName);
        }
        return beanDefinitionMap.get(beanName);
    }
}

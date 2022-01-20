package ruan.cong.summerframework.beans.factory.support;

import java.util.HashMap;
import java.util.Map;
import ruan.cong.summerframework.beans.BeanException;
import ruan.cong.summerframework.beans.factory.ConfigurableListableBeanFactory;
import ruan.cong.summerframework.beans.factory.config.BeanDefinition;
import ruan.cong.summerframework.utils.StringUtils;

public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry, ConfigurableListableBeanFactory {

    private Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();

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
        return beanDefinition;
    }

    @Override
    public boolean containsBeanDefinition(String beanName) throws BeanException {
        return beanDefinitionMap.containsKey(beanName);
    }

    @Override
    public <T> Map<String, T> getBeanOfType(Class<T> type) throws BeanException {
        Map<String, T> beans = new HashMap<>();
        beanDefinitionMap.forEach((beanName, beanDefinition) -> {
            if(type.isAssignableFrom(beanDefinition.getBeanClass())){
                beans.put(beanName, (T)getBean(beanName));
            }
        });
        return beans;
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return beanDefinitionMap.keySet().toArray(new String[0]);
    }

    @Override
    public void preInstantiateSingletons() throws BeanException {

    }
}

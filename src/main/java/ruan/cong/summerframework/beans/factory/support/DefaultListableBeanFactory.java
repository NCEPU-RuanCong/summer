package ruan.cong.summerframework.beans.factory.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ruan.cong.summerframework.beans.BeanException;
import ruan.cong.summerframework.beans.factory.config.BeanDefinition;
import ruan.cong.summerframework.utils.StringUtils;

public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry{

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

    //todo 感觉实现得有点问题
    @Override
    public List<Object> getBean(Class beanType){
        List<BeanDefinition> beanDefinitions = new ArrayList<>();
        List<Object> objects = new ArrayList<>();

        String[] beanDefinitionNames = getBeanDefinitionNames();
        for(String beanName : beanDefinitionNames){
            if(!getBeanDefinition(beanName).getBeanClass().equals(beanType)) continue;
            Object bean = getBean(beanName);
            if(null != bean){
                objects.add(bean);
                continue;
            }
            //todo 这块如何获取？
            Object obj = createBean(beanName);
            objects.add(obj);
        }
        return objects;
    }

    @Override
    public boolean containsBeanDefinition(String beanName) throws BeanException {
        return beanDefinitionMap.containsKey(beanName);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return beanDefinitionMap.keySet().toArray(new String[0]);
    }
}

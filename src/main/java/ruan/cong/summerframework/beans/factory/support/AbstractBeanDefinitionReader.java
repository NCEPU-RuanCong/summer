package ruan.cong.summerframework.beans.factory.support;

import ruan.cong.summerframework.core.io.DefaultResourceLoader;
import ruan.cong.summerframework.core.io.ResourceLoader;

public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader{

    private final BeanDefinitionRegistry registry;

    // 为啥上面的加了final而这个没有加
    private ResourceLoader resourceLoader;

    public AbstractBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this(registry, new DefaultResourceLoader());
    }

    public AbstractBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
        this.registry = registry;
        this.resourceLoader = resourceLoader;
    }

    public BeanDefinitionRegistry getRegistry(){
        return registry;
    }

    public ResourceLoader getResourceLoader(){
        return resourceLoader;
    }
}

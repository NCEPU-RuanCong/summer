package ruan.cong.summerframework.beans.factory.support;

import ruan.cong.summerframework.beans.BeanException;
import ruan.cong.summerframework.core.io.Resource;
import ruan.cong.summerframework.core.io.ResourceLoader;

/**
 *
 * Bean解析的类，那么自然应该有BeanFactory（实际上是一个BeanDefinitionRegistry，）
 * 同时需要获取resource，那么应该有一个ClassLoader类（这个是resource和外部交互的类，并不会直接持有resource类）
 * 注意上面的loadBeanDefinition都是没有返回值的，说明加载的BeanDefinition都是直接注册到了Map中，前文我们持有BeanDefinition的Map
 * 的类是DefaultListableBeanFactory，可想而知这个接口的实现层级至少应该继承自DefaultListableBeanFactory
 *
 */
public interface BeanDefinitionReader {

    BeanDefinitionRegistry getRegistry();

    ResourceLoader getResourceLoader();

    void loadBeanDefinitions(Resource resource) throws BeanException, Exception;
    void loadBeanDefinitions(Resource... resources) throws BeanException;
    void loadBeanDefinitions(String location) throws BeanException;
    void loadBeanDefinitions(String... locations) throws BeanException;
}

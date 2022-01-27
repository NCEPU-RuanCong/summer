package ruan.cong.summerframework.beans.factory.config;

import ruan.cong.summerframework.beans.factory.HierarchicalBeanFactory;
import ruan.cong.summerframework.utils.StringValueResolver;

public interface ConfigurableBeanFactory extends SingletonBeanRegistry, HierarchicalBeanFactory {

    String SCOPE_SINGLETON = "singleton";
    String SCOPE_PROTOTYPE = "prototype";

    void destroySingletons();

    void addEmbeddedValueResolver(StringValueResolver stringValueResolver);

    String resolveEmbeddedValue(String value);
}

package ruan.cong.summerframework.beans.factory.config;

import ruan.cong.summerframework.beans.factory.HierarchicalBeanFactory;

public interface ConfigurableBeanFactory extends SingleBeanFactory, HierarchicalBeanFactory {

    String SCOPE_SINGLETON = "singleton";
    String SCOPE_PROTOTYPE = "prototype";

    void destroySingletons();
}

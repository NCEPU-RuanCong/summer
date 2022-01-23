package ruan.cong.summerframework.beans.context;

import ruan.cong.summerframework.beans.BeanException;

public interface ConfigurableApplicationContext extends ApplicationContext{
    void refresh() throws BeanException;

    void registerShutdownHook();

    void close();
}

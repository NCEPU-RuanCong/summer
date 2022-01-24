package ruan.cong.summerframework.beans.context.event;

import ruan.cong.summerframework.beans.context.ApplicationEvent;
import ruan.cong.summerframework.beans.context.ApplicationListener;
import ruan.cong.summerframework.beans.factory.BeanFactory;

public class SimpleApplicationEventMulticaster extends AbstractApplicationEventMulticaster{

    public SimpleApplicationEventMulticaster(BeanFactory beanFactory) {
        setBeanFactory(beanFactory);
    }

    public void multicastEvent(ApplicationEvent event) {
        for(final ApplicationListener applicationListener : getApplicationListeners(event)){
            applicationListener.onApplicationEvent(event);
        }
    }
}

package ruan.cong.summerframework.beans.context.support;

import ruan.cong.summerframework.beans.context.ApplicationContext;
import ruan.cong.summerframework.beans.context.ApplicationContextAware;
import ruan.cong.summerframework.beans.factory.config.BeanPostProcessor;

public class ApplicationContextAwareProcessor implements BeanPostProcessor {

    private ApplicationContext applicationContext;

    public ApplicationContextAwareProcessor(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        if(bean instanceof ApplicationContextAware){
            ((ApplicationContextAware)bean).setApplicationContext(applicationContext);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        return bean;
    }
}

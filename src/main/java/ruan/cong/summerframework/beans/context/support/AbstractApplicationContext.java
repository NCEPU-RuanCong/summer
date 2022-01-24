package ruan.cong.summerframework.beans.context.support;

import java.awt.*;
import java.util.Collection;
import java.util.Map;
import ruan.cong.summerframework.beans.BeanException;
import ruan.cong.summerframework.beans.context.ApplicationEvent;
import ruan.cong.summerframework.beans.context.ApplicationListener;
import ruan.cong.summerframework.beans.context.ConfigurableApplicationContext;
import ruan.cong.summerframework.beans.context.event.ApplicationEventMulticaster;
import ruan.cong.summerframework.beans.context.event.ContextClosedEvent;
import ruan.cong.summerframework.beans.context.event.ContextRefreshEvent;
import ruan.cong.summerframework.beans.context.event.SimpleApplicationEventMulticaster;
import ruan.cong.summerframework.beans.factory.ConfigurableListableBeanFactory;
import ruan.cong.summerframework.beans.factory.config.BeanFactoryPostProcessor;
import ruan.cong.summerframework.beans.factory.config.BeanPostProcessor;
import ruan.cong.summerframework.core.io.DefaultResourceLoader;

/**
 *
 * 注意这个类给人的感觉有点像DefaultListablebeanFactory
 *
 */
public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {

    public static final String APPLICATION_EVENT_MULTICASTER_BEAN_NAME = "applicationEventMulticaster";

    private ApplicationEventMulticaster applicationEventMulticaster;

    @Override
    public void refresh() throws BeanException {
        System.out.println("============================1、创建BeanFactory，加载BeanDefinition==========================");
        // 1、创建BeanFactory，加载BeanDefinition
        refreshBeanFactory();

        System.out.println("============================2、获取BeanFactory==========================");
        // 2、获取BeanFactory
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();

        // 3、注册ApplicationContextAwareProcessor
        beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));

        System.out.println("============================3、调用BeanFactoryPostProcessor==========================");
        // 4、调用BeanFactoryPostProcessor
        invokeBeanFactoryPostProcessors(beanFactory);

        System.out.println("============================4、注册BeanPostProcessor==========================");
        // 5、注册BeanPostProcessor
        registerBeanPostProcessor(beanFactory);

        System.out.println("============================5、实例化所有剩下的单例Bean==========================");
        // 6、实例化所有剩下的单例Bean
        beanFactory.preInstantiateSingletons();

        // 7、注册钩子函数
        this.registerShutdownHook();

        // 8、初始化时间发布者
        initApplicationEventMulticaster();

        // 9、注册监听器
        registerListeners();

        // 10、刷新完成时间发布
        finishRefresh();
    }

    private void initApplicationEventMulticaster(){
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();
        applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);
        beanFactory.registerSingleton(APPLICATION_EVENT_MULTICASTER_BEAN_NAME, applicationEventMulticaster);
    }

    private void registerListeners(){
        Collection<ApplicationListener> listeners = getBeanOfType(ApplicationListener.class).values();
        for(ApplicationListener applicationListener : listeners){
            applicationEventMulticaster.addApplicationListener(applicationListener);
        }
    }

    private void finishRefresh(){
        applicationEventMulticaster.multicastEvent(new ContextRefreshEvent(this));
    }

    @Override
    public void publishEvent(ApplicationEvent event){
        applicationEventMulticaster.multicastEvent(event);
    }

    protected void refreshBeanFactory() throws BeanException{}

    protected abstract ConfigurableListableBeanFactory getBeanFactory();

    private void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory){
        Map<String, BeanFactoryPostProcessor> beanFactoryPostProcessors = beanFactory.getBeanOfType(BeanFactoryPostProcessor.class);
        for(BeanFactoryPostProcessor beanFactoryPostProcessor : beanFactoryPostProcessors.values()){
            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        }
    }

    private void registerBeanPostProcessor(ConfigurableListableBeanFactory beanFactory){
        Map<String, BeanPostProcessor> beanPostProcessors = beanFactory.getBeanOfType(BeanPostProcessor.class);
        for(BeanPostProcessor beanPostProcessor : beanPostProcessors.values()){
            beanFactory.addBeanPostProcessor(beanPostProcessor);
        }
    }

    @Override
    public void registerShutdownHook(){
        Runtime.getRuntime().addShutdownHook(new Thread(this::close));
    }

    public void close(){
        publishEvent(new ContextClosedEvent(this));

        getBeanFactory().destroySingletons();
    }

    @Override
    public <T> Map<String, T> getBeanOfType(Class<T> type) throws BeanException{
        return getBeanFactory().getBeanOfType(type);
    }

    @Override
    public String[] getBeanDefinitionNames(){
        return getBeanFactory().getBeanDefinitionNames();
    }

    @Override
    public Object getBean(String beanName) {
        return getBeanFactory().getBean(beanName);
    }

    @Override
    public Object getBean(String beanName, Object... args) {
        return getBeanFactory().getBean(beanName, args);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeanException {
        return getBeanFactory().getBean(name, requiredType);
    }

}

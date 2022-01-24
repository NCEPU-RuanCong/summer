package ruan.cong.summerframework.beans.context.support;

import java.util.Map;
import ruan.cong.summerframework.beans.BeanException;
import ruan.cong.summerframework.beans.context.ConfigurableApplicationContext;
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
}

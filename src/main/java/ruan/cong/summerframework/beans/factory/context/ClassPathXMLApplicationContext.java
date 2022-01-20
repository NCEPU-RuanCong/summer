package ruan.cong.summerframework.beans.factory.context;

import java.util.List;
import ruan.cong.summerframework.beans.factory.support.DefaultListableBeanFactory;
import ruan.cong.summerframework.beans.factory.xml.XMLBeanDefinitionReader;

public class ClassPathXMLApplicationContext extends ConfigurableApplicationContext{

    public ClassPathXMLApplicationContext refresh(){

        System.out.println("======================开始创建容器，加载BeanDefinition======================");
        // 1、创建BeanFactory，加载BeanDefinition
        createBeanFactory();
        System.out.println("======================容器创建完毕======================");

        // 2、获取BeanDefinition
        ClassPathXMLApplicationContext beanFactory = getBeanFactory();

        System.out.println("======================调用BeanFactoryPostProcessor======================");
        // 3、调用BeanFactoryPostProcessor
        invokeBeanFactoryPostProcessor(beanFactory);

        System.out.println("==========================注册BeanPostProcessor==========================");
        // 4、注册BeanPostProcessor
        registryBeanPostProcessor(beanFactory);

        System.out.println("==============================实例化所有Bean==============================");
        // 5、实例化Bean
        preInstantiateBean();
        System.out.println("==============================容器刷新完毕=================================");

        return beanFactory;
    }

    @Override
    protected void createBeanFactory(){
        DefaultListableBeanFactory defaultListableBeanFactory = new DefaultListableBeanFactory();

        XMLBeanDefinitionReader xmlBeanDefinitionReader = new XMLBeanDefinitionReader(defaultListableBeanFactory);

        xmlBeanDefinitionReader.loadBeanDefinitions("classpath:Spring.xml");
    }

    @Override
    protected ClassPathXMLApplicationContext getBeanFactory(){
        ClassPathXMLApplicationContext classPathXMLApplicationContext = new ClassPathXMLApplicationContext();
        return classPathXMLApplicationContext;
    }

    protected void invokeBeanFactoryPostProcessor(DefaultListableBeanFactory defaultListableBeanFactory){
        List<Object> beanFactoryPostProcessorList = defaultListableBeanFactory.getBean(BeanFactoryPostProcessor.class);
        for(Object beanFactoryPostProcessor : beanFactoryPostProcessorList){
            if(beanFactoryPostProcessor instanceof BeanFactoryPostProcessor){
                BeanFactoryPostProcessor bfpp = (BeanFactoryPostProcessor)beanFactoryPostProcessor;
                bfpp.afterBeanFactoryPostProcessor();
            }
        }
    }

    @Override
    protected void registryBeanPostProcessor(DefaultListableBeanFactory defaultListableBeanFactory){
        String[] beanDefinitionNames = defaultListableBeanFactory.getBeanDefinitionNames();
        for(String beanName : beanDefinitionNames){
            if(defaultListableBeanFactory.getBeanDefinition(beanName).getBeanClass().equals(BeanPostProcessor.class)){
                defaultListableBeanFactory.getBean(beanName);
            }
        }
    }

    @Override
    protected void preInstantiateBean(){
        String[] beanDefinitionNames = getBeanDefinitionNames();
        for(String beanName : beanDefinitionNames){
            getBean(beanName);
        }
    }
}

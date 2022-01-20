package ruan.cong.summerframework.test;

import ruan.cong.summerframework.beans.PropertyValue;
import ruan.cong.summerframework.beans.PropertyValues;
import ruan.cong.summerframework.beans.context.support.ClassPathXmlApplicationContext;
import ruan.cong.summerframework.beans.factory.config.BeanDefinition;
import ruan.cong.summerframework.beans.factory.config.BeanReference;
import ruan.cong.summerframework.beans.factory.support.DefaultListableBeanFactory;
import ruan.cong.summerframework.beans.factory.xml.XMLBeanDefinitionReader;
import ruan.cong.summerframework.test.bean.UserService;
import ruan.cong.summerframework.test.domain.User;

public class ApiTest {
    public static void main(String[] args) throws ClassNotFoundException {
        ApplicationTest();
//        XMLConfigurationTest();
//        applyPropertyInject();
    }


    private static void ApplicationTest(){
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("classpath:Spring.xml");
        classPathXmlApplicationContext.refresh();

        UserService userServiceBean = (UserService)classPathXmlApplicationContext.getBean("userService");
        userServiceBean.printUsername();
        System.out.println("==============" + userServiceBean + "====================");
        UserService userServiceBean2 = (UserService)classPathXmlApplicationContext.getBean("userService");
        System.out.println("==============" + userServiceBean2 + "====================");
    }

    private static void XMLConfigurationTest(){
        DefaultListableBeanFactory defaultListableBeanFactory = new DefaultListableBeanFactory();
        XMLBeanDefinitionReader beanDefinitionReader = new XMLBeanDefinitionReader(defaultListableBeanFactory);
        beanDefinitionReader.loadBeanDefinitions("classpath:Spring.xml");

        UserService userServiceBean = (UserService)defaultListableBeanFactory.getBean("userService");
        userServiceBean.printUsername();
        System.out.println("==============" + userServiceBean + "====================");
        UserService userServiceBean2 = (UserService)defaultListableBeanFactory.getBean("userService");
        System.out.println("==============" + userServiceBean2 + "====================");
    }

    private static void BeanTestVersion1(){
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        BeanDefinition userServiceBeanDefinition = new BeanDefinition(UserService.class);
        beanFactory.registerBeanDefinition("userService", userServiceBeanDefinition);
        UserService userServiceBean = (UserService)beanFactory.getBean("userService", "RuanCong");
        userServiceBean.printUsername();
        System.out.println("==============" + userServiceBean + "====================");
        UserService userServiceBean2 = (UserService)beanFactory.getBean("userService");
        System.out.println("==============" + userServiceBean2 + "====================");
    }


    private static void applyPropertyInject(){
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        BeanDefinition userServiceBeanDefinition = new BeanDefinition(UserService.class);
        BeanDefinition userBeanDefinition = new BeanDefinition(User.class);
        PropertyValues propertyValues = new PropertyValues();
        PropertyValues userPropertyValues = new PropertyValues();
        PropertyValue propertyValue1 = new PropertyValue("number", "666");
        PropertyValue propertyValue2 = new PropertyValue("name", "ruancong");
        PropertyValue propertyValue3 = new PropertyValue("user", new BeanReference("user"));
        propertyValues.addPropertyValue(propertyValue1);
        propertyValues.addPropertyValue(propertyValue2);
        propertyValues.addPropertyValue(propertyValue3);
        userServiceBeanDefinition.setPropertyValues(propertyValues);

        PropertyValue userPropertyValue1 = new PropertyValue("number", 114);
        PropertyValue userPropertyValue2 = new PropertyValue("name", "NCEPU");
        PropertyValue userPropertyValue3 = new PropertyValue("title", "Java softwareEngineering");
        userPropertyValues.addPropertyValue(userPropertyValue1);
        userPropertyValues.addPropertyValue(userPropertyValue2);
        userPropertyValues.addPropertyValue(userPropertyValue3);
        userBeanDefinition.setPropertyValues(userPropertyValues);

        beanFactory.registerBeanDefinition("userService", userServiceBeanDefinition);
        beanFactory.registerBeanDefinition("user", userBeanDefinition);
        UserService userServiceBean = (UserService)beanFactory.getBean("userService", "RuanCong");
        System.out.println(userServiceBean.toString());
        System.out.println("==============" + userServiceBean.getClass() + "====================");
        UserService userServiceBean2 = (UserService)beanFactory.getBean("userService");
        System.out.println("==============" + userServiceBean2.getClass() + "====================");
    }
}

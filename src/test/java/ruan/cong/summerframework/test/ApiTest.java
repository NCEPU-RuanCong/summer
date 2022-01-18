package ruan.cong.summerframework.test;

import ruan.cong.summerframework.beans.PropertyValue;
import ruan.cong.summerframework.beans.PropertyValues;
import ruan.cong.summerframework.beans.factory.config.BeanDefinition;
import ruan.cong.summerframework.beans.factory.support.DefaultListableBeanFactory;
import ruan.cong.summerframework.test.bean.UserService;
import ruan.cong.summerframework.test.domain.User;

public class ApiTest {
    public static void main(String[] args){
        applyPropertyInject();
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
        PropertyValues propertyValues = new PropertyValues();
        PropertyValue propertyValue1 = new PropertyValue("number", "666");
        PropertyValue propertyValue2 = new PropertyValue("name", "ruancong");
        PropertyValue propertyValue3 = new PropertyValue("user", new User(1, "ruancong", "java softwareEngineering"));
        propertyValues.addPropertyValue(propertyValue1);
        propertyValues.addPropertyValue(propertyValue2);
        propertyValues.addPropertyValue(propertyValue3);
        userServiceBeanDefinition.setPropertyValues(propertyValues);
        beanFactory.registerBeanDefinition("userService", userServiceBeanDefinition);

        UserService userServiceBean = (UserService)beanFactory.getBean("userService", "RuanCong");
        System.out.println(userServiceBean.toString());
        System.out.println("==============" + userServiceBean + "====================");
        UserService userServiceBean2 = (UserService)beanFactory.getBean("userService");
        System.out.println("==============" + userServiceBean2 + "====================");
    }
}

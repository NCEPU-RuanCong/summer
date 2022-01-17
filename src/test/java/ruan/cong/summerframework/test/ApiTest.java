package ruan.cong.summerframework.test;

import ruan.cong.summerframework.beans.factory.config.BeanDefinition;
import ruan.cong.summerframework.beans.factory.support.DefaultListableBeanFactory;
import ruan.cong.summerframework.test.bean.UserService;

public class ApiTest {
    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        BeanDefinition userServiceBeanDefinition = new BeanDefinition(UserService.class);
        beanFactory.registerBeanDefinition("userService", userServiceBeanDefinition);
        UserService userServiceBean = (UserService)beanFactory.getBean("userService");
        userServiceBean.printInfo();
        System.out.println("==============" + userServiceBean + "====================");
        UserService userServiceBean2 = (UserService)beanFactory.getBean("userService");
        System.out.println("==============" + userServiceBean2 + "====================");
    }
}

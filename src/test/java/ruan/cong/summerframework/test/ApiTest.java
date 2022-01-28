package ruan.cong.summerframework.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.time.LocalDate;
import java.util.Date;
import java.util.EventObject;
import org.junit.Test;
import ruan.cong.summerframework.aop.AdvisedSupport;
import ruan.cong.summerframework.aop.MethodMatcher;
import ruan.cong.summerframework.aop.TargetSource;
import ruan.cong.summerframework.aop.aspectj.AspectJExpressionPointcut;
import ruan.cong.summerframework.aop.framework.Cglib2AopProxy;
import ruan.cong.summerframework.aop.framework.JdkDynamicAopProxy;
import ruan.cong.summerframework.aop.framework.ReflectiveMethodInvocation;
import ruan.cong.summerframework.beans.PropertyValue;
import ruan.cong.summerframework.beans.PropertyValues;
import ruan.cong.summerframework.beans.context.event.ApplicationContextEvent;
import ruan.cong.summerframework.beans.context.support.ClassPathXmlApplicationContext;
import ruan.cong.summerframework.beans.factory.config.BeanDefinition;
import ruan.cong.summerframework.beans.factory.config.BeanReference;
import ruan.cong.summerframework.beans.factory.support.DefaultListableBeanFactory;
import ruan.cong.summerframework.beans.factory.xml.XMLBeanDefinitionReader;
import ruan.cong.summerframework.test.aop.AopInterface;
import ruan.cong.summerframework.test.aop.AopTestService;
import ruan.cong.summerframework.test.aop.IUserService;
import ruan.cong.summerframework.test.aop.UserServiceInterceptor;
import ruan.cong.summerframework.test.bean.UserService;
import ruan.cong.summerframework.test.domain.User;
import ruan.cong.summerframework.test.event.CustomerEvent;
import org.aopalliance.intercept.MethodInterceptor;
import ruan.cong.summerframework.test.service.TestService;

/**
 *
 *
 * 这章有个问题，这样操作createBean，那么前置处理器不就失效了吗？
 *
 * 这里有需要注意的第二个点就是AOP创建出来的代理Bean不是单例的？Spring的本身实现是如何的呢？
 *
 * 这里的第三个问题就是构造函数的问题，使用cg的方式创建Bean的时候，如果该Bean只有带参数的构造函数那么会报错，必须加一个无参构造函数，感觉有点呆
 * 试试Spring本身是不是这样的？如果不是怎么解决的？
 *
 */

public class ApiTest {
    public static void main(String[] args) throws Exception {
        SpringAop();
//        test_dynamic();
//        AOPTest();
//        AopLearning();
//        eventTest();
//        factoryBeanTest();
//        awareTest();
//        initAndDestroyMethodTest();
//        ApplicationTest();
//        XMLConfigurationTest();
//        applyPropertyInject();
    }

    @Test
    public void testProxyInjectValue(){
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:Spring.xml");
        IUserService userService = applicationContext.getBean("userServiceAOP", IUserService.class);
        System.out.println(userService.toString());
    }

    @Test
    public void testAnnotationValue(){
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring-scan.xml");
        TestService userService = applicationContext.getBean("userService", TestService.class);
        System.out.println(userService.toString());
    }

    @Test
    public void testScan(){
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring-scan.xml");
        IUserService userService = applicationContext.getBean("userService", IUserService.class);
        // 这里并没有解决在配置文件中配置的注入信息，因为只扫描了class类路径
        System.out.println("测试结果" + userService.queryUserInfo() + "\n" + userService);
    }

    @Test
    public void testProperty(){
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring-property.xml");
        IUserService userService = applicationContext.getBean("userService", IUserService.class);
        System.out.println("测试结果" + userService);
    }

    private static void SpringAop(){
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
//        applicationContext.refresh();
        IUserService userService = applicationContext.getBean("userServiceAOP", IUserService.class);
        IUserService userService_1 = applicationContext.getBean("userServiceAOP", IUserService.class);
        System.out.println("测试结果：" + userService.queryUserInfo());
        System.out.println(userService + "\n" + userService_1);
    }

    private static void test_dynamic(){
        IUserService iUserService = new ruan.cong.summerframework.test.aop.UserService();


        // 代理
        AdvisedSupport advisedSupport = new AdvisedSupport();
        advisedSupport.setTargetSource(new TargetSource(iUserService));
        advisedSupport.setMethodInterceptor(new UserServiceInterceptor());
        advisedSupport.setMethodMatcher(new AspectJExpressionPointcut("execution(* ruan.cong.summerframework.test.aop.IUserService.*(..))"));

        // 代理对象
//        IUserService proxy = (IUserService) new JdkDynamicAopProxy(advisedSupport).getProxy();
        IUserService proxy = (IUserService) new Cglib2AopProxy(advisedSupport).getProxy();

        System.out.println(proxy.queryUserInfo());
    }

    private static void AOPTest() throws NoSuchMethodException {
        AspectJExpressionPointcut aspectJExpressionPointcut = new AspectJExpressionPointcut("execution(* ruan.cong.summerframework.test.aop.AopTestService.*(..))");
        Class<AopTestService> clazz = AopTestService.class;
        Method[] declaredMethods = clazz.getDeclaredMethods();
        System.out.println(declaredMethods[0].getName());
        /**
         *
         * 原来getMethod(具体方法名字,方法参数...)这才是正确使用姿势
         *
         */
        Method method = clazz.getDeclaredMethod("aopTest", String.class);
        System.out.println(aspectJExpressionPointcut.matches(clazz));
        System.out.println(aspectJExpressionPointcut.matches(method, clazz));

        Class<UserService> clazz_1 = UserService.class;
        Method method1 = clazz_1.getDeclaredMethod("companyPrint", String.class);
        System.out.println(aspectJExpressionPointcut.matches(clazz_1));
        System.out.println(aspectJExpressionPointcut.matches(method1, clazz_1));
    }

    private static void AopLearning(){
        Object target = new AopTestService();
        AopInterface proxy = (AopInterface) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                target.getClass().getInterfaces(), new InvocationHandler() {
                    MethodMatcher methodMatcher = new AspectJExpressionPointcut("execution(* ruan.cong.summerframework.test.aop.AopTestService.*(..))");

                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        if(methodMatcher.matches(method, proxy.getClass())){
                            MethodInterceptor methodInterceptor = invocation -> {
                                System.out.println("代理开始。。。");
                                try {
                                    return invocation.proceed();
                                } finally {
                                    System.out.println("代理完毕!");
                                }
                            };
                            System.out.println("方法执行开始...");
                            Object result = methodInterceptor.invoke(new ReflectiveMethodInvocation(target, method, args));
                            System.out.println("方法执行完毕!");
                            return result;
                        }
                        return method.invoke(target, args);
                    }
                });
        proxy.aopTest("aop-test");
    }

    private static void eventTest(){
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("classpath:Spring.xml");
        classPathXmlApplicationContext.refresh();
        CustomerEvent customerEvent = new CustomerEvent(classPathXmlApplicationContext, "the end of world", LocalDate.now(), "GOD");
        classPathXmlApplicationContext.publishEvent(customerEvent);

        UserService userServiceBean = (UserService)classPathXmlApplicationContext.getBean("userService");
        userServiceBean.printUsername();
        System.out.println("==============" + userServiceBean + "====================");
        UserService userServiceBean2 = (UserService)classPathXmlApplicationContext.getBean("userService");
        System.out.println("==============" + userServiceBean2 + "====================");
        userServiceBean.companyPrint("jindi");
        CustomerEvent customerEvent_2 = new CustomerEvent(classPathXmlApplicationContext, "f****", LocalDate.now(), "AHAHAH");
        classPathXmlApplicationContext.publishEvent(customerEvent_2);
    }

    private static void factoryBeanTest(){
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("classpath:Spring.xml");
        classPathXmlApplicationContext.refresh();

        UserService userServiceBean = (UserService)classPathXmlApplicationContext.getBean("userService");
        userServiceBean.printUsername();
        System.out.println("==============" + userServiceBean + "====================");
        UserService userServiceBean2 = (UserService)classPathXmlApplicationContext.getBean("userService");
        System.out.println("==============" + userServiceBean2 + "====================");
        userServiceBean.companyPrint("jindi");
    }

    private static void awareTest(){
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("classpath:Spring.xml");
        classPathXmlApplicationContext.refresh();

        UserService userServiceBean = (UserService)classPathXmlApplicationContext.getBean("userService");
        userServiceBean.printUsername();
        System.out.println("==============" + userServiceBean + "====================");
        UserService userServiceBean2 = (UserService)classPathXmlApplicationContext.getBean("userService");
        System.out.println("==============" + userServiceBean2 + "====================");
    }

    private static void initAndDestroyMethodTest(){
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("classpath:Spring.xml");
        classPathXmlApplicationContext.refresh();

        UserService userServiceBean = (UserService)classPathXmlApplicationContext.getBean("userService");
        userServiceBean.printUsername();
        System.out.println("==============" + userServiceBean + "====================");
        UserService userServiceBean2 = (UserService)classPathXmlApplicationContext.getBean("userService");
        System.out.println("==============" + userServiceBean2 + "====================");
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

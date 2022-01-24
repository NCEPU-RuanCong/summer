package ruan.cong.summerframework.test.bean;

import ruan.cong.summerframework.beans.BeanException;
import ruan.cong.summerframework.beans.context.ApplicationContext;
import ruan.cong.summerframework.beans.context.ApplicationContextAware;
import ruan.cong.summerframework.beans.factory.BeanClassLoaderAware;
import ruan.cong.summerframework.beans.factory.BeanFactory;
import ruan.cong.summerframework.beans.factory.BeanFactoryAware;
import ruan.cong.summerframework.beans.factory.BeanNameAware;
import ruan.cong.summerframework.test.domain.User;
import ruan.cong.summerframework.test.mapper.Company;

public class UserService implements ApplicationContextAware, BeanFactoryAware, BeanNameAware, BeanClassLoaderAware {

    private ApplicationContext applicationContext;
    private BeanFactory beanFactory;
    private String beanName;
    private ClassLoader classLoader;

    private String name;
    private String number;

    private User user;
    private Company company;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public UserService(String name) {
        this.name = name;
    }

    public UserService() {
    }

    public UserService(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public UserService(String name, String number, User user) {
        this.name = name;
        this.number = number;
        this.user = user;
    }

    public void printInfo(){
        System.out.println("UserService!");
    }

    public void printUsername(){
        System.out.println("UserService---userName:" + name + "\nnumber:" + number);
    }

    public void myInitMethod(){
        System.out.println("==============UserService的initMethod开始执行================");
    }

    public void myDestroyMethod(){
        System.out.println("==============UserService的<<destroyMethod开始执行================");
    }

    @Override
    public String toString() {
        return "UserService{" +
                "name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", user=" + user +
                '}';
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeanException {
        this.applicationContext = applicationContext;
        System.out.println("======获取到了applicationContext=======");
        String[] beanNames = this.applicationContext.getBeanDefinitionNames();
        for (String beanName : beanNames){
            System.out.println("---->创建了BeanName：" + beanName + "\n");
        }
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
        System.out.println("=====感知到ClassLoader=====");
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeanException {
        this.beanFactory = beanFactory;
        System.out.println("======获取到了BeanFactory======");
        // 这个竟然没有获取所有Bean的名字
    }

    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
        System.out.println("=======感知到了beanName：" + beanName);
    }

    public void companyPrint(String name){
        company.printCompanyName(name);
    }
}

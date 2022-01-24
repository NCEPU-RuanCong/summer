package ruan.cong.summerframework.beans.factory.config;

public interface SingletonBeanRegistry {
    Object getSingletonBean(String beanName);

    void registerSingleton(String beanName, Object singletonObject);
}

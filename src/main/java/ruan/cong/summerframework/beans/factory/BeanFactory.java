package ruan.cong.summerframework.beans.factory;

public interface BeanFactory {
    Object getBean(String beanName);

    Object getBean(String beanName, Object... args);
}

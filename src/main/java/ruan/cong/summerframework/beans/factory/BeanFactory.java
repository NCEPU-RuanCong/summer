package ruan.cong.summerframework.beans.factory;

import java.util.List;

public interface BeanFactory {
    Object getBean(String beanName);

    Object getBean(String beanName, Object... args);

    List<Object> getBean(Class beanType);
}

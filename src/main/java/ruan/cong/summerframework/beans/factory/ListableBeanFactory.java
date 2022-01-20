package ruan.cong.summerframework.beans.factory;

import java.util.Map;
import ruan.cong.summerframework.beans.BeanException;

public interface ListableBeanFactory extends BeanFactory{
    <T> Map<String, T> getBeanOfType(Class<T> type) throws BeanException;

    String[] getBeanDefinitionNames();
}

package ruan.cong.summerframework.beans.factory;

import ruan.cong.summerframework.beans.BeanException;

public interface BeanFactoryAware extends Aware{
    void setBeanFactory(BeanFactory beanFactory) throws BeanException;
}

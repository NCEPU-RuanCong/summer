package ruan.cong.summerframework.beans.factory;

import ruan.cong.summerframework.beans.BeanException;

public interface ObjectFactory<T> {

    T getObject() throws BeanException;
}

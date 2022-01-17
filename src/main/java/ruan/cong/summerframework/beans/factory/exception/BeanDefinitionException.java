package ruan.cong.summerframework.beans.factory.exception;

import ruan.cong.summerframework.beans.BeanException;

public class BeanDefinitionException extends BeanException {
    public BeanDefinitionException(){}
    public BeanDefinitionException(String message){
        super(message);
    }
}

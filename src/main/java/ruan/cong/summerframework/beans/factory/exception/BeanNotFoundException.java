package ruan.cong.summerframework.beans.factory.exception;

import ruan.cong.summerframework.beans.BeanException;

public class BeanNotFoundException extends BeanException {
    public BeanNotFoundException(){}
    public BeanNotFoundException(String message){
        super(message);
    }
}

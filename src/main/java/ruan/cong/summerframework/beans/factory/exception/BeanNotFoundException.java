package ruan.cong.summerframework.beans.factory.exception;

public class BeanNotFoundException extends RuntimeException{
    public BeanNotFoundException(){}
    public BeanNotFoundException(String message){
        super(message);
    }
}

package ruan.cong.summerframework.beans;

public class BeanException extends RuntimeException{
    public BeanException(){}
    public BeanException(String message){
        super(message);
    }
    public BeanException(String message, Exception e){
        super(message, e);
    }
}

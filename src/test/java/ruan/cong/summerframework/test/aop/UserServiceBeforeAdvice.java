package ruan.cong.summerframework.test.aop;

import java.lang.reflect.Method;
import ruan.cong.summerframework.aop.MethodBeforeAdvice;

public class UserServiceBeforeAdvice implements MethodBeforeAdvice {
    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("before aop ：" + method.getName());
    }
}

package ruan.cong.summerframework.test.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class UserServiceInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Long start = System.currentTimeMillis();
        System.out.println("监控-begin by aop");
        try {
            return invocation.proceed();
        } finally {
            System.out.println("方法名称: " + invocation.getMethod().getName());
            System.out.println("方法耗时:" + (System.currentTimeMillis() - start) + " ms");
            System.out.println("监控 - end\n");
        }
    }
}

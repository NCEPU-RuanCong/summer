package ruan.cong.summerframework.aop;


import org.aopalliance.intercept.MethodInterceptor;

public class AdvisedSupport {
    private boolean isProxyTarget;

    private TargetSource targetSource;
    private MethodInterceptor methodInterceptor;
    private MethodMatcher methodMatcher;

    public void setProxyTargetClass(boolean proxyTarget) {
        isProxyTarget = proxyTarget;
    }

    public boolean isProxyTargetClass(){
        return isProxyTarget;
    }

    public TargetSource getTargetSource() {
        return targetSource;
    }

    public void setTargetSource(TargetSource targetSource) {
        this.targetSource = targetSource;
    }

    public MethodInterceptor getMethodInterceptor() {
        return methodInterceptor;
    }

    public void setMethodInterceptor(MethodInterceptor methodInterceptor) {
        this.methodInterceptor = methodInterceptor;
    }

    public MethodMatcher getMethodMatcher() {
        return methodMatcher;
    }

    public void setMethodMatcher(MethodMatcher methodMatcher) {
        this.methodMatcher = methodMatcher;
    }
}

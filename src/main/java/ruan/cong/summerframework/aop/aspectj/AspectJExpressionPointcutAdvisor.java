package ruan.cong.summerframework.aop.aspectj;

import org.aopalliance.aop.Advice;
import ruan.cong.summerframework.aop.Pointcut;
import ruan.cong.summerframework.aop.PointcutAdvisor;

public class AspectJExpressionPointcutAdvisor implements PointcutAdvisor {

    private AspectJExpressionPointcut pointcut;

    private Advice advice;

    private String expression;

    public void setExpression(String expression) {
        this.expression = expression;
    }

    @Override
    public Pointcut getPointcut() {
        if(null == pointcut) return new AspectJExpressionPointcut(expression);
        return pointcut;
    }

    @Override
    public Advice getAdvice() {
        return advice;
    }

    public void setAdvice(Advice advice) {
        this.advice = advice;
    }
}

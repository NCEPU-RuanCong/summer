package ruan.cong.summerframework.aop;

/**
 *
 * Advisor = advice + pointcut
 * advice = 什么时候执行切面，before,after,around
 * pointcut = 谁被执行？连接join point
 *
 */

public interface PointcutAdvisor extends Advisor{
    Pointcut getPointcut();
}

package ruan.cong.summerframework.aop.framework.autoproxy;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import ruan.cong.summerframework.aop.*;
import ruan.cong.summerframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import ruan.cong.summerframework.aop.framework.ProxyFactory;
import ruan.cong.summerframework.beans.BeanException;
import ruan.cong.summerframework.beans.PropertyValues;
import ruan.cong.summerframework.beans.factory.BeanFactory;
import ruan.cong.summerframework.beans.factory.BeanFactoryAware;
import ruan.cong.summerframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import ruan.cong.summerframework.beans.factory.support.DefaultListableBeanFactory;

/**
 *
 *
 * 注意看这个类通过实现BeanFactoryAware类，获取到了beanFactory，然后进行了一系列的Bean的操作，比如提前按照自己的想法
 * 实例化Bean，这里就是实现了AOP，返回的是代理Bean
 * 我最开始以为是在原来的Bean基础上加工，原来这里直接是在Bean的实例化之前就拦下来自己操作了
 *
 *
 */
public class DefaultAdvisorAutoProxyCreator implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {

    private DefaultListableBeanFactory beanFactory;

    private final Set<Object> earlyProxyReference = Collections.synchronizedSet(new HashSet<Object>());

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeanException {
        this.beanFactory = (DefaultListableBeanFactory)beanFactory;
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeanException {
        return null;
    }

    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName) throws BeanException {
        return null;
    }

    private boolean isInfrastructureClass(Class beanClass){
        return Advisor.class.isAssignableFrom(beanClass) || Pointcut.class.isAssignableFrom(beanClass) || Advice.class.isAssignableFrom(beanClass);
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        if (!earlyProxyReference.contains(beanName)) {
            return wrapIfNecessary(bean, beanName);
        }
        return bean;
    }

    protected Object wrapIfNecessary(Object bean, String beanName) {
        Class beanClass = bean.getClass();

        if (isInfrastructureClass(beanClass)) return bean;

        Collection<AspectJExpressionPointcutAdvisor> advisors = beanFactory.getBeanOfType(AspectJExpressionPointcutAdvisor.class).values();

        for (AspectJExpressionPointcutAdvisor advisor : advisors) {
            ClassFilter classFilter = advisor.getPointcut().getClassFilter();
            if (!classFilter.matches(beanClass)) continue;

            AdvisedSupport advisedSupport = new AdvisedSupport();

            TargetSource targetSource = new TargetSource(bean);

            advisedSupport.setTargetSource(targetSource);
            advisedSupport.setMethodMatcher(advisor.getPointcut().getMethodMatcher());
            advisedSupport.setMethodInterceptor((MethodInterceptor)advisor.getAdvice());

            advisedSupport.setProxyTargetClass(false);

            return new ProxyFactory(advisedSupport).getProxy();
        }
        return bean;
    }

    public Object getEarlyBeanReference(Object bean, String beanName) {
        earlyProxyReference.add(beanName);
        return wrapIfNecessary(bean, beanName);
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeanException {
        return true;
    }
}

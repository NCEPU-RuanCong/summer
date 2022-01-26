package ruan.cong.summerframework.aop.framework.autoproxy;

import java.util.Collection;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import ruan.cong.summerframework.aop.*;
import ruan.cong.summerframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import ruan.cong.summerframework.aop.framework.ProxyFactory;
import ruan.cong.summerframework.beans.BeanException;
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

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeanException {
        this.beanFactory = (DefaultListableBeanFactory)beanFactory;
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeanException {

        if (isInfrastructureClass(beanClass)) return null;

        Collection<AspectJExpressionPointcutAdvisor> advisors = beanFactory.getBeanOfType(AspectJExpressionPointcutAdvisor.class).values();

        for (AspectJExpressionPointcutAdvisor advisor : advisors) {
            ClassFilter classFilter = advisor.getPointcut().getClassFilter();
            if (!classFilter.matches(beanClass)) continue;

            AdvisedSupport advisedSupport = new AdvisedSupport();

            TargetSource targetSource = null;

            try {
                targetSource = new TargetSource(beanClass.getDeclaredConstructor().newInstance());
            } catch (Exception e) {
                throw new BeanException("aop target create failed! target :" + beanClass.getName());
            }
            advisedSupport.setTargetSource(targetSource);
            advisedSupport.setMethodMatcher(advisor.getPointcut().getMethodMatcher());
            advisedSupport.setMethodInterceptor((MethodInterceptor)advisor.getAdvice());

            advisedSupport.setProxyTargetClass(false);

            return new ProxyFactory(advisedSupport).getProxy();
        }

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
        return bean;
    }
}

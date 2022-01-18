package ruan.cong.summerframework.beans.factory.support;

import java.lang.reflect.Constructor;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;
import ruan.cong.summerframework.beans.BeanException;
import ruan.cong.summerframework.beans.factory.config.BeanDefinition;

public class CglibSubclassingInstantiationStrategy implements InstantiationStrategy{
    @Override
    public Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor ctor, Object[] args) throws BeanException {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(beanDefinition.getBeanClass());
        enhancer.setCallback(new NoOp() {
            @Override
            public int hashCode() {
                return super.hashCode();
            }
        });
        if(null == ctor) return enhancer.create();
        return enhancer.create(ctor.getParameterTypes(), args);
    }
}

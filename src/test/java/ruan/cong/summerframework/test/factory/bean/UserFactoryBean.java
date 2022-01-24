package ruan.cong.summerframework.test.factory.bean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import ruan.cong.summerframework.beans.factory.FactoryBean;
import ruan.cong.summerframework.test.domain.User;
import ruan.cong.summerframework.test.mapper.Company;

public class UserFactoryBean implements FactoryBean {
    @Override
    public Company getObject(){
        InvocationHandler handler = (proxy, method, args) -> {
            if ("toString".equals(method.getName())) return this.toString();
            System.out.println(("proxy :" + method.getName() + "，你被代理了,args:" + Arrays.toString(args)));
            return proxy;
        };
        return (Company) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{Company.class}, handler);
    }

    @Override
    public Class<?> getObjectType() {
        return User.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}

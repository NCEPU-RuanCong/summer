package ruan.cong.summerframework.beans.context.event;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;
import ruan.cong.summerframework.beans.BeanException;
import ruan.cong.summerframework.beans.context.ApplicationEvent;
import ruan.cong.summerframework.beans.context.ApplicationListener;
import ruan.cong.summerframework.beans.factory.BeanFactory;
import ruan.cong.summerframework.beans.factory.BeanFactoryAware;
import ruan.cong.summerframework.utils.ClassUtils;

public class AbstractApplicationEventMulticaster implements ApplicationEventMulticaster, BeanFactoryAware {

    private final Set<ApplicationListener<ApplicationEvent>> applicationListeners = new LinkedHashSet<>();

    private BeanFactory beanFactory;

    @Override
    public void addApplicationListener(ApplicationListener<?> applicationListener) {
        applicationListeners.add((ApplicationListener<ApplicationEvent>)applicationListener);
    }

    @Override
    public void removeApplicationListener(ApplicationListener<?> applicationListener) {
        applicationListeners.remove((ApplicationListener<ApplicationEvent>)applicationListener);
    }

    @Override
    public void multicastEvent(ApplicationEvent event) {

    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeanException {
        this.beanFactory = beanFactory;
    }

    protected Collection<ApplicationListener> getApplicationListeners(ApplicationEvent applicationEvent){

        LinkedList<ApplicationListener> allListeners = new LinkedList<>();

        for(ApplicationListener<ApplicationEvent> applicationEventApplicationListener : this.applicationListeners){
            if(supportsEvent(applicationEventApplicationListener, applicationEvent)) allListeners.add(applicationEventApplicationListener);
        }

        return allListeners;
    }

    private boolean supportsEvent(ApplicationListener<ApplicationEvent> applicationListener, ApplicationEvent applicationEvent){
        Class<? extends ApplicationListener> listenerClass = applicationListener.getClass();
        Class<?> targetClass = ClassUtils.isCglibProxyClass(listenerClass) ? listenerClass.getSuperclass() : listenerClass;

        Type genericInterface = targetClass.getGenericInterfaces()[0];
        Type actualTypeArgument = ((ParameterizedType)genericInterface).getActualTypeArguments()[0];
        String className = actualTypeArgument.getTypeName();

        Class<?> eventClass;

        try {
            eventClass = Class.forName(className);
        } catch (Exception e){
            throw new BeanException("judge application listener support event error!");
        }
        /**
         * 这个是判断类的，instanceod是判断对象是否是子对象的
         * Class类还有哥方法isInstance(Object obj)如果obj是Class的子类，则返回true，否则返回false
         * a.isAssignableFrom(b),如果a是b的同类或者父类那么返回true，否则返回false
         */
        return eventClass.isAssignableFrom(applicationEvent.getClass());
    }
}

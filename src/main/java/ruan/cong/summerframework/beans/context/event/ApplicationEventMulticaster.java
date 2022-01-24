package ruan.cong.summerframework.beans.context.event;

import ruan.cong.summerframework.beans.context.ApplicationEvent;
import ruan.cong.summerframework.beans.context.ApplicationListener;

public interface ApplicationEventMulticaster {

    void addApplicationListener(ApplicationListener<?> applicationListener);

    void removeApplicationListener(ApplicationListener<?> applicationListener);

    void multicastEvent(ApplicationEvent event);
}

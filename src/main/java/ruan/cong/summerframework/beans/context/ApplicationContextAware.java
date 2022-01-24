package ruan.cong.summerframework.beans.context;

import ruan.cong.summerframework.beans.BeanException;
import ruan.cong.summerframework.beans.factory.Aware;

public interface ApplicationContextAware extends Aware {
    void setApplicationContext(ApplicationContext applicationContext) throws BeanException;
}

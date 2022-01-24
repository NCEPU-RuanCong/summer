package ruan.cong.summerframework.beans.context.support;

import ruan.cong.summerframework.beans.BeanException;

public class ClassPathXmlApplicationContext extends AbstractXmlApplicationContext{

    private String[] configLocations;

    public ClassPathXmlApplicationContext() {
    }

    public ClassPathXmlApplicationContext(String configLocation) {
        this(new String[]{configLocation});
    }

    public ClassPathXmlApplicationContext(String[] configLocations) {
        this.configLocations = configLocations;
    }

    @Override
    protected String[] getConfigLocations(){
        return configLocations;
    }
}

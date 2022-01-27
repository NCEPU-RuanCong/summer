package ruan.cong.summerframework.beans.factory;

import java.util.List;
import java.util.Properties;
import ruan.cong.summerframework.beans.BeanException;
import ruan.cong.summerframework.beans.PropertyValue;
import ruan.cong.summerframework.beans.PropertyValues;
import ruan.cong.summerframework.beans.factory.config.BeanDefinition;
import ruan.cong.summerframework.beans.factory.config.BeanFactoryPostProcessor;
import ruan.cong.summerframework.core.io.DefaultResourceLoader;
import ruan.cong.summerframework.core.io.Resource;

public class PropertyPlaceholderConfigurer implements BeanFactoryPostProcessor {

    public static final String DEFAULT_PLACEHOLDER_PREFIX = "${";

    public static final String DEFAULT_PLACEHOLDER_SUFFIX = "}";

    private String location;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        try {
            DefaultResourceLoader defaultResourceLoader = new DefaultResourceLoader();
            Resource resource = defaultResourceLoader.getResource(location);
            Properties properties = new Properties();
            /**
             * 可以这样操作？
             */
            properties.load(resource.getInputStream());

            String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();

            for (String beanName : beanDefinitionNames) {
                BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
                PropertyValues propertyValues = beanDefinition.getPropertyValues();
                for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {
                    Object value = propertyValue.getValue();
                    if ( !(value instanceof String) ) continue;
                    String strVal = (String) value;
                    StringBuilder buffer = new StringBuilder(strVal);
                    /**
                     *
                     * 这么写的话Spring的@Value中不能乱带}的字符，不然会解析错误，稍等验证下在Spring中是不是如此
                     *
                     */
                    int startIdx = strVal.indexOf(DEFAULT_PLACEHOLDER_PREFIX);
                    int stopIdx = strVal.indexOf(DEFAULT_PLACEHOLDER_SUFFIX);
                    if (startIdx != -1 && stopIdx != -1 && startIdx < stopIdx) {
                        String propertyKey = strVal.substring(startIdx + 2, stopIdx);
                        String propertyVal = properties.getProperty(propertyKey);
                        buffer.replace(startIdx, stopIdx + 1, propertyVal);
                        propertyValues.addPropertyValue(new PropertyValue(propertyValue.getName(), buffer.toString()));
                    }
                }
            }
        } catch (Exception e) {
            throw new BeanException("could not load properties", e);
        }
    }

    public void setLocation(String location) {
        this.location = location;
    }
}

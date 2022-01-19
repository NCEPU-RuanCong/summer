package ruan.cong.summerframework.beans.factory.xml;

import cn.hutool.core.util.XmlUtil;
import java.io.IOException;
import java.io.InputStream;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import ruan.cong.summerframework.beans.BeanException;
import ruan.cong.summerframework.beans.PropertyValue;
import ruan.cong.summerframework.beans.factory.config.BeanDefinition;
import ruan.cong.summerframework.beans.factory.config.BeanReference;
import ruan.cong.summerframework.beans.factory.exception.ResourceException;
import ruan.cong.summerframework.beans.factory.support.AbstractBeanDefinitionReader;
import ruan.cong.summerframework.beans.factory.support.BeanDefinitionRegistry;
import ruan.cong.summerframework.core.io.Resource;
import ruan.cong.summerframework.core.io.ResourceLoader;
import ruan.cong.summerframework.utils.StringUtils;

public class XMLBeanDefinitionReader extends AbstractBeanDefinitionReader {

    public XMLBeanDefinitionReader(BeanDefinitionRegistry registry){
        super(registry);
    }

    public XMLBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader){
        super(registry, resourceLoader);
    }

    @Override
    public void loadBeanDefinitions(Resource resource) throws BeanException {
        try (InputStream inputStream = resource.getInputStream()) {
            doLoadBeanDefinitions(inputStream);
        } catch (IOException | ClassNotFoundException e) {
            throw new BeanException("IOException parsing XML document form " + resource, e);
        }
    }

    @Override
    public void loadBeanDefinitions(Resource... resources) throws BeanException {
        for(Resource resource : resources){
            loadBeanDefinitions(resource);
        }
    }

    @Override
    public void loadBeanDefinitions(String location) throws BeanException {
        if(StringUtils.isEmpty(location)){
            throw new ResourceException("empty resource path");
        }
        Resource resource = getResourceLoader().getResource(location);
        loadBeanDefinitions(resource);
    }

    private void doLoadBeanDefinitions(InputStream inputStream) throws ClassNotFoundException{
        Document doc = XmlUtil.readXML(inputStream);
        Element root = doc.getDocumentElement();
        NodeList childNodes = root.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++){
            // 不是element的标签不处理
            if(!(childNodes.item(i) instanceof Element)) continue;
            // 暂时先只考虑bean标签
            if(!("bean".equals(childNodes.item(i).getNodeName()))) continue;

            // 解析节点及其属性
            Element bean = (Element) childNodes.item(i);
            String id = bean.getAttribute("id");
            String name = bean.getAttribute("name");
            String className = bean.getAttribute("class");

            Class<?> clazz = Class.forName(className);
            // id优先级 > name
            String beanName = StringUtils.nonEmpty(id) ? id : name;

            BeanDefinition beanDefinition = new BeanDefinition(clazz);

            // 读取属性并且填充
            for (int j = 0; j < bean.getChildNodes().getLength(); j++){
                if (!(bean.getChildNodes().item(j) instanceof Element)) continue;
                if(!("property".equals(bean.getChildNodes().item(j).getNodeName()))) continue;

                Element property = (Element) bean.getChildNodes().item(j);
                String attrName = property.getAttribute("name");
                String attrValue = property.getAttribute("value");
                String attrRef = property.getAttribute("ref");

                Object value = StringUtils.nonEmpty(attrRef) ? new BeanReference(attrRef) : attrValue;
                PropertyValue propertyValue = new PropertyValue(attrName, value);
                beanDefinition.getPropertyValues().addPropertyValue(propertyValue);
            }
            if(getRegistry().containsBeanDefinition(beanName)){
                throw new BeanException(beanName + " already exist!");
            }
            getRegistry().registerBeanDefinition(beanName, beanDefinition);
        }
    }
}

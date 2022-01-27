package ruan.cong.summerframework.beans.factory.xml;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
//import org.w3c.dom.Document;
//import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import ruan.cong.summerframework.beans.BeanException;
import ruan.cong.summerframework.beans.PropertyValue;
import ruan.cong.summerframework.beans.context.annotation.ClassPathBeanDefinitionScanner;
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
        } catch (Exception e) {
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

    @Override
    public void loadBeanDefinitions(String... locations) throws BeanException{
        if(null == locations || locations.length <= 0){
            throw new ResourceException("empty resource path");
        }
        for(String location : locations){
            loadBeanDefinitions(location);
        }
    }

    private void doLoadBeanDefinitions(InputStream inputStream) throws Exception {

        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        Element root = document.getRootElement();

        Element componentScan = root.element("component-scan");
        if (null != componentScan) {
            String scanPath = componentScan.attributeValue("base-package");
            if(StringUtils.isEmpty(scanPath)) {
                throw new BeanException("base package path is wrong!");
            }
            scanPackage(scanPath);
        }
        List<Element> beanList = root.elements("bean");

        for (Element bean : beanList) {
            String id = bean.attributeValue("id");
            String name = bean.attributeValue("name");
            String className = bean.attributeValue("class");
            String initMethod = bean.attributeValue("init-method");
            String destroyMethodName = bean.attributeValue("destroy-method");
            String beanScope = bean.attributeValue("scope");

            Class<?> clazz = Class.forName(className);

            String beanName = StringUtils.nonEmpty(id) ? id : name;
            if (StringUtils.isEmpty(beanName)) {
                beanName = StringUtils.lowerFirst(clazz.getSimpleName());
            }

            BeanDefinition beanDefinition = new BeanDefinition(clazz);
            beanDefinition.setInitMethodName(initMethod);
            beanDefinition.setDestroyMethodName(destroyMethodName);

            if (StringUtils.nonEmpty(beanScope)) {
                beanDefinition.setScope(beanScope);
            }

            List<Element> propertyList = bean.elements("property");
            for(Element property : propertyList) {
                String attrName = property.attributeValue("name");
                String attrValue = property.attributeValue("value");
                String attrRef = property.attributeValue("ref");

                Object value = StringUtils.nonEmpty(attrRef) ? new BeanReference(attrRef) : attrValue;
                PropertyValue propertyValue = new PropertyValue(attrName, value);

                beanDefinition.getPropertyValues().addPropertyValue(propertyValue);
            }
            if (getRegistry().containsBeanDefinition(beanName)) {
               throw new BeanException("Duplicate beanName[" + beanName + "] is not allowed");
            }
            getRegistry().registerBeanDefinition(beanName, beanDefinition);
        }
    }

    /**
     *
     * beandefinition解析的version-1
     *
     * @param scanPath
     */
//    private void doLoadBeanDefinitions(InputStream inputStream) throws Exception {
//        // 替换成Spring使用的解析方式
////        Document doc = XmlUtil.readXML(inputStream);
////        Element root = doc.getDocumentElement();
////        NodeList childNodes = root.getChildNodes();
//
//        for (int i = 0; i < childNodes.getLength(); i++){
//            // 不是element的标签不处理
//            if(!(childNodes.item(i) instanceof Element)) continue;
//            // 暂时先只考虑bean标签
//            if(!("bean".equals(childNodes.item(i).getNodeName()))) continue;
//
//            // 解析节点及其属性
//            Element bean = (Element) childNodes.item(i);
//            String id = bean.getAttribute("id");
//            String name = bean.getAttribute("name");
//            String className = bean.getAttribute("class");
//            String initMethod = bean.getAttribute("init-method");
//            String destroyMethod = bean.getAttribute("destroy-method");
//            String scope = bean.getAttribute("scope");
//
//            Class<?> clazz = Class.forName(className);
//            // id优先级 > name
//            String beanName = StringUtils.nonEmpty(id) ? id : name;
//
//            BeanDefinition beanDefinition = new BeanDefinition(clazz);
//
//            // 读取属性并且填充
//            for (int j = 0; j < bean.getChildNodes().getLength(); j++){
//                if (!(bean.getChildNodes().item(j) instanceof Element)) continue;
//                if(!("property".equals(bean.getChildNodes().item(j).getNodeName()))) continue;
//
//                Element property = (Element) bean.getChildNodes().item(j);
//                String attrName = property.getAttribute("name");
//                String attrValue = property.getAttribute("value");
//                String attrRef = property.getAttribute("ref");
//
////                initMethod = StringUtils.isEmpty(initMethod) ? property.getAttribute("init-method") : initMethod;
////                destroyMethod = StringUtils.isEmpty(destroyMethod) ? property.getAttribute("destroy-method") : destroyMethod;
////                scope = StringUtils.isEmpty(scope) ? property.getAttribute("scope") : scope;
//
//                Object value = StringUtils.nonEmpty(attrRef) ? new BeanReference(attrRef) : attrValue;
//                PropertyValue propertyValue = new PropertyValue(attrName, value);
//                beanDefinition.getPropertyValues().addPropertyValue(propertyValue);
//            }
//
//            if(StringUtils.nonEmpty(initMethod)) beanDefinition.setInitMethodName(initMethod);
//
//            if(StringUtils.nonEmpty(destroyMethod)) beanDefinition.setDestroyMethodName(destroyMethod);
//
//            beanDefinition.setScope(scope);
//
//            if(getRegistry().containsBeanDefinition(beanName)){
//                throw new BeanException(beanName + " already exist!");
//            }
//            getRegistry().registerBeanDefinition(beanName, beanDefinition);
//        }
//    }

    private void scanPackage(String scanPath){
        String[] basePackages = scanPath.split(",");
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(getRegistry());
        scanner.doScan(basePackages);
    }
}

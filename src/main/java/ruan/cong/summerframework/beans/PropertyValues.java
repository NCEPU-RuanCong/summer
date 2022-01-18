package ruan.cong.summerframework.beans;

import java.util.ArrayList;
import java.util.List;

public class PropertyValues {
    private List<PropertyValue> propertyValueList = new ArrayList<>();

    public void addPropertyValue(PropertyValue propertyValue){
        propertyValueList.add(propertyValue);
    }

    public List<PropertyValue> getPropertyValues(){
        return propertyValueList;
    }

    public PropertyValue getPropertyValue(String name){
        for (PropertyValue propertyValue : propertyValueList){
            if (propertyValue.getName().equals(name)){
                return propertyValue;
            }
        }
        return null;
    }
}

package ruan.cong.summerframework.utils;

import java.lang.reflect.Field;
import ruan.cong.summerframework.beans.BeanException;

/**
 *
 * bean的相关操作
 *
 */
public class BeanUtils {
    public static void setFieldValue(Object bean, String name, Object value){
        try {
            Class<?> clazz = bean.getClass();
            Class<?> superclass = bean.getClass().getSuperclass();
            Field[] declaredFields = clazz.getDeclaredFields();
            fillField(declaredFields, bean, name, value);
            if(null != superclass){
                Field[] superclassDeclaredFields = superclass.getDeclaredFields();
                fillField(superclassDeclaredFields, bean, name, value);
            }
        } catch (SecurityException | IllegalArgumentException e) {
            throw new BeanException("create bean failed" + name);
        }
    }

    private static void fillField(Field[] fields, Object bean, String name, Object value){
        for(Field field : fields){
            if (field.getName().equals(name)) {
                field.setAccessible(true);
                try {
                    field.set(bean, value);
                } catch (IllegalAccessException e) {
                    throw new BeanException("create bean failed" + name);
                }
                return;
            }
        }
    }
}

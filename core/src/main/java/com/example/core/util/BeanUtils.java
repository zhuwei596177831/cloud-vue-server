package com.example.core.util;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 朱伟伟
 * @date 2022-06-02 10:46:43
 * @description BeanUtils 工具类
 */
public abstract class BeanUtils extends org.springframework.beans.BeanUtils {

    public static void setProperty(Object obj, String propertyName, Object value) throws Exception {
        Class clazz = obj.getClass();   //获取字节码对象
        Field field = clazz.getDeclaredField(propertyName); //暴力反射获取字段
        field.setAccessible(true); //设置访问权限
        Class typeClass = field.getType();
        System.out.println(typeClass); //获取字段类型
        Constructor con = typeClass.getConstructor(typeClass);//类型转化
        Object setVlue = con.newInstance(value);
        field.set(obj, setVlue);   //设置值
    }


    /**
     * 去掉bean中所有属性为字符串的前后空格
     *
     * @param bean
     * @throws Exception
     */
    public static void beanAttributeValueTrim(Object bean) throws Exception {
        if (bean != null) {
            //获取所有的字段包括public,private,protected,private
            Field[] fields = bean.getClass().getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                Field f = fields[i];
                if (f.getType().getName().equals("java.lang.String")) {
                    String key = f.getName();//获取字段名
                    Object value = getFieldValue(bean, key);

                    if (value == null)
                        continue;

                    setFieldValue(bean, key, value.toString().trim());
                }
            }
        }
    }

    /**
     * 利用反射通过get方法获取bean中字段fieldName的值
     *
     * @param bean
     * @param fieldName
     * @return
     * @throws Exception
     */
    public static Object getFieldValue(Object bean, String fieldName)
            throws Exception {
        StringBuffer result = new StringBuffer();
        String methodName = result.append("get")
                .append(fieldName.substring(0, 1).toUpperCase())
                .append(fieldName.substring(1)).toString();

        Object rObject = null;
        Method method = null;

        @SuppressWarnings("rawtypes")
        Class[] classArr = new Class[0];
        method = bean.getClass().getMethod(methodName, classArr);
        rObject = method.invoke(bean, new Object[0]);

        return rObject;
    }

    /**
     * 利用发射调用bean.set方法将value设置到字段
     *
     * @param bean
     * @param fieldName
     * @param value
     * @throws Exception
     */
    public static void setFieldValue(Object bean, String fieldName, Object value)
            throws Exception {
        StringBuilder result = new StringBuilder();
        String methodName = result.append("set")
                .append(fieldName.substring(0, 1).toUpperCase())
                .append(fieldName.substring(1)).toString();

        /**
         * 利用发射调用bean.set方法将value设置到字段
         */
        Class[] classArr = new Class[1];
        classArr[0] = "java.lang.String".getClass();
        Method method = bean.getClass().getMethod(methodName, classArr);
        method.invoke(bean, value);
    }


    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }
        if (obj instanceof String) {
            if (((String) obj).trim().length() == 0) {
                return true;
            }
        } else if (obj instanceof Collection) {
            if (((Collection) obj).isEmpty()) {
                return true;
            }
        } else if (obj.getClass().isArray()) {
            if (((Object[]) obj).length == 0) {
                return true;
            }
        } else if (obj instanceof Map) {
            if (((Map) obj).isEmpty()) {
                return true;
            }
        } else {
            return false;
        }
        return false;
    }

    static List<String> getAllFieldNames(Class<?> cls) {
        List<String> list = new ArrayList<>();
        List<Field> fields = getAllDeclareFields(cls);
        list.addAll(fields.stream().map(Field::getName).collect(Collectors.toList()));
        return list;
    }

    private static List<Field> getAllDeclareFields(Class<?> cls) {
        List<Field> list = new ArrayList<>();
        Collections.addAll(list, cls.getDeclaredFields());
        return list;
    }

    /**
     * List<实体>转为list<Object>
     *
     * @param t
     * @return
     */
    public static List<Object> beanToObject(List<?> t) {
        List<Object> o = new ArrayList<Object>();
        Iterator<?> it = t.iterator();
        while (it.hasNext()) {
            o.add(it.next());
        }

        return o;
    }

    // 对象转换成Map
    public static Map<String, Object> obj2Map(Object obj) {
        Map<String, Object> map = new HashMap<String, Object>();
        // System.out.println(obj.getClass());
        // 获取f对象对应类中的所有属性域
        Field[] fields = obj.getClass().getDeclaredFields();
        for (int i = 0, len = fields.length; i < len; i++) {
            String varName = fields[i].getName();
            //varName = varName.toLowerCase();//将key置为小写，默认为对象的属性
            try {
                // 获取原来的访问控制权限
                boolean accessFlag = fields[i].isAccessible();
                // 修改访问控制权限
                fields[i].setAccessible(true);
                // 获取在对象f中属性fields[i]对应的对象中的变量
                Object o = fields[i].get(obj);
                if (o != null)
                    map.put(varName, o);
                // 恢复访问控制权限
                fields[i].setAccessible(accessFlag);
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                ex.printStackTrace();
            }
        }
        return map;
    }

    /**
     * 拷贝属性，但不适用null替换原属性
     *
     * @param source 源
     * @return String[]
     */
    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        return emptyNames.toArray(new String[0]);
    }

    /**
     * 对象拷时，null值不进行复制
     *
     * @param src:
     * @param target:
     * @author: 朱伟伟
     * @date: 2022-06-02 10:47
     **/
    public static void copyPropertiesIgnoreNull(Object src, Object target) {
        copyProperties(src, target, getNullPropertyNames(src));
    }

}

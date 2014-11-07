package com.kull;

import com.kull.datetime.DateFormatter;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Timestamp;
import java.text.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.beanutils.PropertyUtils;

public class Clazz {

    public static <T> T valueOf(Class<T> cls, Object val, T defaultVal) {
        T t = defaultVal;
        try {
            t = valueOf(cls, val);
        } catch (Exception ex) {

        }
        return t;
    }

    public static <T> T valueOf(Class<T> cls, Object val) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Method method = cls.getMethod("valueOf", cls);
        return (T) method.invoke(cls, val);
    }

    @SuppressWarnings("unchecked")
    public static <T> T valueOf(String value, T defaultValue) {

        T t = defaultValue;

        if (value == null) {
            return t;
        }
        try {
            if (t instanceof String) {
                t = (T) value;
            } else if (t instanceof Character && value.length() > 0) {
                t = (T) Character.valueOf(value.charAt(0));
            } else if (t instanceof Integer) {
                t = (T) Integer.valueOf(value);
            } else if (t instanceof Double) {
                t = (T) Double.valueOf(value);
            } else if (t instanceof Float) {
                t = (T) Float.valueOf(value);
            } else if (t instanceof BigDecimal) {
                t = (T) BigDecimal.valueOf(Long.valueOf(value));
            } else if (t instanceof Long) {
                t = (T) Long.valueOf(value);
            } else if (t instanceof Date) {
                // t = (T) Calendarz.parse(value);
            } else if (t instanceof Timestamp) {
                t = (T) Timestamp.valueOf(value);
            } else if (t instanceof Boolean) {
                if (value.equalsIgnoreCase("Y")) {
                    t = (T) Boolean.TRUE;
                } else {
                    t = (T) Boolean.valueOf(value);
                }
            } else if (t instanceof Byte[]) {
                t = (T) value.getBytes();
            }
        } catch (Exception e) {
            t = defaultValue;
        }
        return t;
    }

    public static <T> T valueOf(String value, T defaultValue, Class<T> cls) {
        T t = defaultValue;

        if (value == null) {
            return t;
        }
        try {
            if (String.class.equals(cls)) {
                t = (T) value;
            } else if (Character.class.equals(cls) && value.length() > 0) {
                t = (T) Character.valueOf(value.charAt(0));
            } else if (Integer.class.equals(cls)) {
                t = (T) Integer.valueOf(value);
            } else if (Double.class.equals(cls)) {
                t = (T) Double.valueOf(value);
            } else if (Float.class.equals(cls)) {
                t = (T) Float.valueOf(value);
            } else if (BigDecimal.class.equals(cls)) {
                t = (T) BigDecimal.valueOf(Long.valueOf(value));
            } else if (Long.class.equals(cls)) {
                t = (T) Long.valueOf(value.toString());
            } else if (Date.class.equals(cls)) {
                t = (T) DateFormatter.parsez(value);
            } else if (Timestamp.class.equals(cls)) {
                t = (T) Timestamp.valueOf(value);
            } else if (Boolean.class.equals(cls)) {
                if (value.equalsIgnoreCase("Y")) {
                    t = (T) Boolean.TRUE;
                } else {
                    t = (T) Boolean.valueOf(value);
                }
            } else if (t instanceof Byte[]) {
                t = (T) value.getBytes();
            }
        } catch (Exception e) {
            t = defaultValue;
        }
        return t;
    }

    @SuppressWarnings("all")
    public static boolean isAnyEmpty(Object... os) {
        boolean isanyempty = false;
        for (Object o : os) {
            if (isEmpty(o)) {
                isanyempty = true;
                break;
            }
        }
        return isanyempty;
    }

    @SuppressWarnings("all")
    public static boolean isEmpty(Object o) {
        if (o == null) {
            return true;
        } else if (o instanceof String) {
            return Stringz.isBlank((String) o);
        } else if (o instanceof Collection) {
            return ((Collection) o).isEmpty();
        } else if (o.getClass().isArray()) {
            return Array.getLength(o) == 0;
        } else if (o instanceof Map) {
            return ((Map) o).isEmpty();
        }

        return false;
    }

    public static boolean isNotEmpty(Object o) {
        return !isEmpty(o);
    }

    @SuppressWarnings("all")
    public static Map toMap(Object[] array, String... keys) {
        if (array == null) {
            return new HashMap();
        }
        Map m = new LinkedHashMap();
        for (int i = 0; i < keys.length; i++) {
            if (array.length == i) {
                break;
            }
            m.put(keys[i], array[i]);
        }
        return m;
    }

    public static boolean isInterface(Class c, String szInterface) {
        Class[] face = c.getInterfaces();
        for (int i = 0, j = face.length; i < j; i++) {
            if (face[i].getName().equals(szInterface)) {
                return true;
            } else {
                Class[] face1 = face[i].getInterfaces();
                for (int x = 0; x < face1.length; x++) {
                    if (face1[x].getName().equals(szInterface)) {
                        return true;
                    } else if (isInterface(face1[x], szInterface)) {
                        return true;
                    }
                }
            }
        }
        if (null != c.getSuperclass()) {
            return isInterface(c.getSuperclass(), szInterface);
        }
        return false;
    }

    public static <T> List<T> toList(Map<Object, T> map) {
        List<T> listReturn = new ArrayList();
        for (Iterator<Object> it = map.keySet().iterator(); it.hasNext();) {
            Object key = it.next();
            listReturn.add(map.get(key));
        }
        return listReturn;

    }

    public static <T> List<T> list(T... ts) {
        List<T> list = new ArrayList<T>();
        for (T t : ts) {
            list.add(t);
        }
        return list;
    }

    public static <T> boolean isEquals(T obj1, T obj2) {
        if (obj1 != null && obj2 != null) {
            return obj1.equals(obj2);
        } else if (obj1 == null && obj2 == null) {
            return true;
        }
        return false;
    }

    public static <T> boolean isNotEquals(T obj1, T obj2) {
        return !isEquals(obj1, obj2);
    }

    public static <T> boolean isIn(T obj1, T... objs) {
        for (T obj : objs) {
            if (isEquals(obj1, obj)) {
                return true;
            }
        }
        return false;
    }

    public static <T> boolean isNotIn(T obj1, T... objs) {
        return !isIn(obj1, objs);
    }

    public static <T> Class<T> actualTypeBy(Class cls, int index) {

        return actualTypeBy(cls, index, 0);

    }

    public static <T> Class<T> actualTypeBy(Class cls, int index, int interfaceIndex) {
        ParameterizedType parameterizedType = null;
        if (cls.isInterface()) {
            parameterizedType = (ParameterizedType) cls.getGenericInterfaces()[interfaceIndex];
        } else {
            parameterizedType = (ParameterizedType) cls.getGenericSuperclass();
        }
        Class<T> clz = (Class<T>) parameterizedType.getActualTypeArguments()[index];
        return clz;

    }

    public static <A extends Annotation> A annotationBy(Class cls, Class<A> annCls) throws NullPointerException {
        Class superCls = cls;
        A ann = (A) superCls.getAnnotation(annCls);

        while (ann == null && !Object.class.equals(superCls)) {
            //throw new NullPointerException("Annotation is not exist");
            try {
                superCls = superCls.getSuperclass();
                ann = (A) superCls.getAnnotation(annCls);
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
        return ann;
    }

    public static Type getTypez(Type type, int index) {
        if (type instanceof Class<?>) {
            return Object.class;
        } else if (type instanceof ParameterizedType) {
            return ((ParameterizedType) type).getActualTypeArguments()[index];
        } else if (type instanceof GenericArrayType) {
            return getTypez(((GenericArrayType) type).getGenericComponentType(), index);
        } else {
            throw new IllegalArgumentException("Type \'" + type + "\' is not a Class, "
                    + "ParameterizedType, or GenericArrayType. Can't extract class.");
        }
    }

    public static Class[] getAllClass(Class c) {

        List<Class> lClassReturn = new ArrayList<Class>();
        lClassReturn.add(c);
        if (!c.getSuperclass().equals(Object.class)) {

            for (Class cls : Clazz.getAllClass(c)) {
                lClassReturn.add(cls);
            }
        }
        Class[] cs = lClassReturn.toArray(new Class[lClassReturn.size()]);

        return cs;
    }

    public static Field[] allDeclaredFieldsBy(Class c) {

        List<Field> fields = new ArrayList<Field>();
        for (Class cls : Clazz.getAllClass(c)) {
            for (Field field : cls.getDeclaredFields()) {
                if (isThis0(field)) {
                    continue;
                }
                fields.add(field);
            }
        }
        Field[] fs = fields.toArray(new Field[fields.size()]);

        return fs;
    }

    public static Set<Method> getAllDeclaredMethods(Class c) {
        Set<Method> methods = new HashSet<Method>();
        for (Class cls : Clazz.getAllClass(c)) {
            for (Method method : cls.getDeclaredMethods()) {
                methods.add(method);

            }
        }
        return methods;
    }

    public static Method setterBy(Class c, Field field) throws NoSuchMethodException, SecurityException {

        return settersBy(c).get(field.getName());
    }

    public static Method getterBy(Class c, Field field) throws NoSuchMethodException, SecurityException {

        return gettersBy(c).get(field.getName());
    }

    public static Method setterBy(Class c, String fieldName) throws NoSuchMethodException, SecurityException {

        return settersBy(c).get(fieldName);
    }

    public static Method getterBy(Class c, String fieldName) throws NoSuchMethodException, SecurityException {

        return gettersBy(c).get(fieldName);
    }

    public static Map<String, Method> settersBy(Class c) throws NoSuchMethodException, SecurityException {

        Map<String, Method> setters = new HashMap<String, Method>();
        for (Field field : Clazz.allDeclaredFieldsBy(c)) {
            String settername = "set" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
            setters.put(field.getName(), c.getMethod(settername, field.getType()));
        }

        return setters;
    }

    public static Map<String, Method> gettersBy(Class c) throws NoSuchMethodException, SecurityException {

        Map<String, Method> getters = new HashMap<String, Method>();
        for (Field field : Clazz.allDeclaredFieldsBy(c)) {
            String settername = "get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
            getters.put(field.getName(), c.getMethod(settername));
        }

        return getters;
    }

    public static void attr(Object obj, String pattern, Object value) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        PropertyUtils.setProperty(obj, pattern, value);
    }

    public static Object attr(Object obj, String pattern) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        return PropertyUtils.getProperty(obj, pattern);
    }

    public static <T> void cp(T source, T target) throws Exception {
        // TODO Auto-generated method stub
        if (source == null || target == null) {
            throw new NullPointerException("source or target can't be null");
        }
        Map<String, Method> getters = gettersBy(source.getClass()), setters = settersBy(source.getClass());
        for (Map.Entry<String, Method> entrySet : getters.entrySet()) {
            String key = entrySet.getKey();
            Method getter = entrySet.getValue(), setter = setters.get(key);
            if (getter == null || setter == null) {
                continue;
            }
            Object val = getter.invoke(source);
            if (val == null) {
                continue;
            }
            setter.invoke(target, val);
        }
    }

    public static Type fieldType(Object obj, String pattern) throws Exception {
        // TODO Auto-generated method stub
        boolean isOk = false;
        Class[] lAllClass = getAllClass(obj.getClass());
        Type t = null;
        //String lTempName="set"+StringHelper.format(pattern, EStringFormat.upcaseFirstChar);
        for (Class c : lAllClass) {
            try {
                Field f = c.getDeclaredField(pattern);
                if (f != null) {
                    t = f.getType();
                    isOk = true;
                    break;
                }
            } catch (Exception ex) {
            }
        }
        if (!isOk) {
            throw new Exception(MessageFormat.format("This Model have not \"{0}\" ", pattern));
        }

        return t;
    }

    public static boolean isThis0(Field field) {
        return field.getName().equals("this$0");
    }

    //无限级内部类实例化
    public static <T> T newInstance(Class<T> cls) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        T t = null;
        String clsname = cls.getName();
        int i = clsname.lastIndexOf("$");
        if (i > -1) {
            Constructor constr = cls.getConstructors()[0];
            String pname = clsname.substring(0, i);
            Class pcls = null;
            try {
                pcls = Class.forName(pname);
            } catch (ClassNotFoundException ex) {
                throw new IllegalArgumentException(ex);
            }
            t = (T) constr.newInstance(newInstance(pcls));
        } else {
            t = (T) cls.newInstance();
        }
        return t;
    }

    public static void refEnv(Class cls) {
        refEnv(cls, cls.getName()+".");
    }

    public static void refEnv(Class cls, String prefix) {
        Field[] fields = cls.getDeclaredFields();
        
        for (Field field : fields) {
            String fileallname = prefix + field.getName();
            String envname = fileallname.replace(".", "_");
        
            String envval = System.getenv(envname.toUpperCase());
            if(envval==null)continue;
            try {
                 if (instanceOfArray(field,String.class)) {
                        field.set(cls, envval.split(","));
                }else if(isNumber(field)){
                       field.set(cls, (Number)valueOf(envval, null));
                } else {

                    field.set(cls, envval);

                }
            } catch (Exception ex) {

            }

        }
    }

    public static <T> Set<T> newSet(T... vals) {
        Set<T> tset = new HashSet<T>();
        for (T val : vals) {
            tset.add(val);
        }
        return tset;
    }

    

    public static boolean instanceOf(Field field,Class cls) {
        return cls.getName().equals(field.getType().getName());
    }

    public static boolean instanceOfArray(Field field,Class cls) {
        return ("[L"+cls.getName()+";").equals(field.getType().getName());
    }

    public static boolean isNumber(Field field) {
        return Clazz.isIn(field.getType().getName()
                ,Double.class.getName()
                ,double.class.getName()
                ,int.class.getName()
                ,Integer.class.getName()
                ,Float.class.getName()
                ,float.class.getName()
                ,long.class.getName()
                ,Long.class.getName()
                
                );
    }

}

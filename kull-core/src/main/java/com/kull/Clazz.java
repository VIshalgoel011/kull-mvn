package com.kull;

import com.kull.datetime.DateFormatter;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.math.BigDecimal;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Timestamp;
import java.text.*;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
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
           t= valueOf(cls, value);
        } finally{
          return t;
        }
        
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

    public static void injectEnv(Class cls) {
        injectEnv(cls, cls.getName() + ".");
    }

    public static void injectEnv(Class cls, String prefix) {
        Field[] fields = cls.getDeclaredFields();

        for (Field field : fields) {
            String fileallname = prefix + field.getName();
            String envname = fileallname.replace(".", "_");

            String envval = System.getenv(envname.toUpperCase());
            if (envval == null) {
                continue;
            }
            try {
                if (instanceOfArray(field, String.class)) {
                    field.set(cls, envval.split(","));
                } else if (isNumber(field)) {
                    field.set(cls, (Number) valueOf(envval, null));
                } else {

                    field.set(cls, envval);

                }
            } catch (Exception ex) {

            }

        }
    }

   

    public static boolean instanceOf(Field field, Class cls) {
        return cls.getName().equals(field.getType().getName());
    }

    public static boolean instanceOfArray(Field field, Class cls) {
        return ("[L" + cls.getName() + ";").equals(field.getType().getName());
    }

    public static boolean isNumber(Field field) {
        return Clazz.isIn(field.getType().getName(), Double.class.getName(), double.class.getName(), int.class.getName(), Integer.class.getName(), Float.class.getName(), float.class.getName(), long.class.getName(), Long.class.getName()
        );
    }

    public static <T> List<Class<? extends T>> genericClassIn(Class<T> generic) throws IOException, ClassNotFoundException {
        Package[] pacs = Package.getPackages();
        List<Class<? extends T>> clss = new ArrayList<Class<? extends T>>();
        for (Package pac : pacs) {
            clss.addAll(genericClassIn(pac, generic));
        }
        return clss;
    }

    public static <T> List<Class<? extends T>> genericClassIn(Package pac, Class<T> generic) throws IOException, ClassNotFoundException {

        List<Class<? extends T>> classTes = new ArrayList<Class<? extends T>>();

        for (Class<?> classe : classesIn(pac.getName())) {
            if (isGeneric(classe, generic)) {
                classTes.add((Class<? extends T>) classe);
            }
        }

        return classTes;
    }

    public static List<Class> implementClassIn(Package pac, Class... impls) throws IOException, ClassNotFoundException {

        List<Class> classTes = new ArrayList<Class>();

        for (Class<?> classe : classesIn(pac.getName())) {
            if (isImplements(classe, impls)) {
                classTes.add(classe);
            }
        }
        return classTes;
    }

    public static boolean isGeneric(Class<?> source, Class<?> gengric) {

        Class ptcls = (Class) source.getSuperclass();
        while (ptcls != null) {
            if (ptcls == gengric) {
                return true;
            } else {
                ptcls = ptcls.getSuperclass();
            }
        }
        return false;
    }

    public static Set<Class> implementsBy(Class cls) {
        Class ptcls = (Class) cls;
        Set<Class> ainterfaces = new HashSet<Class>();
        while (ptcls != null) {
            Class[] ifs = ptcls.getInterfaces();
            if (ifs.length > 0) {
                for (Class aIf : ifs) {
                    ainterfaces.add(aIf);
                }
            } else {
                ptcls = ptcls.getSuperclass();
            }
        }
        return ainterfaces;
    }

    public static boolean isImplements(Class source, Class... interfaces) {

        Class ptcls = (Class) source;
        Set<Class> ainterfaces = implementsBy(ptcls);
        for (Class aInterface : interfaces) {
            if (!ainterfaces.contains(aInterface)) {
                return false;
            }
        }
        return true;
    }

    public static Set<Class<?>> classesIn(String pack) throws IOException {

        // 第一个class类的集合
        Set<Class<?>> classes = new LinkedHashSet<Class<?>>();
        // 是否循环迭代
        boolean recursive = true;
        // 获取包的名字 并进行替换
        String packageName = pack;
        String packageDirName = packageName.replace('.', '/');
        // 定义一个枚举的集合 并进行循环来处理这个目录下的things
        Enumeration<URL> dirs = Thread.currentThread().getContextClassLoader().getResources(
                packageDirName);
        // 循环迭代下去
        while (dirs.hasMoreElements()) {
            // 获取下一个元素
            URL url = dirs.nextElement();
            // 得到协议的名称
            String protocol = url.getProtocol();
            // 如果是以文件的形式保存在服务器上
            if ("file".equals(protocol)) {

                // 获取包的物理路径
                String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                // 以文件的方式扫描整个包下的文件 并添加到集合中
                classes.addAll(classesIn(packageName, filePath, recursive));
            } else if ("jar".equals(protocol)) {

                JarFile jar = ((JarURLConnection) url.openConnection())
                        .getJarFile();
                // 从此jar包 得到一个枚举类
                Enumeration<JarEntry> entries = jar.entries();
                // 同样的进行循环迭代
                while (entries.hasMoreElements()) {
                    // 获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
                    JarEntry entry = entries.nextElement();
                    String name = entry.getName();
                    // 如果是以/开头的
                    if (name.charAt(0) == '/') {
                        // 获取后面的字符串
                        name = name.substring(1);
                    }
                    // 如果前半部分和定义的包名相同
                    if (!name.startsWith(packageDirName)) {
                        continue;
                    }
                    int idx = name.lastIndexOf('/');
                    // 如果以"/"结尾 是一个包
                    if (idx != -1) {
                        // 获取包名 把"/"替换成"."
                        packageName = name.substring(0, idx)
                                .replace('/', '.');
                    }
                    // 如果可以迭代下去 并且是一个包
                    if ((idx != -1) || recursive) {
                        // 如果是一个.class文件 而且不是目录
                        if (name.endsWith(".class")
                                && !entry.isDirectory()) {
                            // 去掉后面的".class" 获取真正的类名
                            String className = name.substring(
                                    packageName.length() + 1, name
                                    .length() - 6);
                            try {
                                // 添加到classes
                                classes.add(Class
                                        .forName(packageName + '.'
                                                + className));
                            } catch (ClassNotFoundException e) {
                                throw new IOException(e);
                            }
                        }
                    }
                }

            }
        }

        return classes;
    }

    private static Set<Class<?>> classesIn(String packageName,
            String packagePath, final boolean recursive) {
        Set<Class<?>> classes = new HashSet<Class<?>>();
        // 获取此包的目录 建立一个File
        File dir = new File(packagePath);
        // 如果不存在或者 也不是目录就直接返回
        if (!dir.exists() || !dir.isDirectory()) {
            // log.warn("用户定义包名 " + packageName + " 下没有任何文件");
            return classes;
        }
        // 如果存在 就获取包下的所有文件 包括目录
        File[] dirfiles = dir.listFiles(new FileFilter() {
            // 自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)
            public boolean accept(File file) {
                return (recursive && file.isDirectory())
                        || (file.getName().endsWith(".class"));
            }
        });
        // 循环所有文件
        for (File file : dirfiles) {
            // 如果是目录 则继续扫描
            if (file.isDirectory()) {
                classes.addAll(classesIn(packageName + "."
                        + file.getName(), file.getAbsolutePath(), recursive)
                );
            } else {
                // 如果是java类文件 去掉后面的.class 只留下类名
                String className = file.getName().substring(0,
                        file.getName().length() - 6);
                try {
                    // 添加到集合中去
                    //classes.add(Class.forName(packageName + '.' + className));
                    //经过回复同学的提醒，这里用forName有一些不好，会触发static方法，没有使用classLoader的load干净
                    classes.add(Thread.currentThread().getContextClassLoader().loadClass(packageName + '.' + className));
                } catch (ClassNotFoundException e) {
                    // log.error("添加用户自定义视图类错误 找不到此类的.class文件");
                    e.printStackTrace();
                }
            }
        }
        return classes;
    }

}

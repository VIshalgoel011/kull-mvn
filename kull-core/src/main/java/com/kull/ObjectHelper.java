package com.kull;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.math.BigDecimal;

import java.sql.Timestamp;
import java.text.*;
import java.util.*;













public class ObjectHelper {

	
	
	
	public static <T> Set<T> evalProperties(Collection collection,String name){
		Set<T> propertis=new HashSet<T>();
		for(Object obj:collection){
			T t=null;
			try {
				t = attr(obj, name);
				propertis.add(t);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return propertis;
	} 
	
    @SuppressWarnings("unchecked")
	public  static <T> T parse(String value,  T defaultValue){
        
		T t=defaultValue;
		
		if(value==null){
			return t;
		}
		try{
		if(t instanceof String){
			t=(T) value;
		}else if(t instanceof Character&&value.length()>0){
			t=(T)Character.valueOf(value.charAt(0));
		}
		else if(t instanceof Integer){
			t=(T) Integer.valueOf(value);
		}else if(t instanceof Double){
			t=(T) Double.valueOf(value);
		}else if(t instanceof Float){
			t=(T) Float.valueOf(value);
		}else if(t instanceof BigDecimal){
			t=(T) BigDecimal.valueOf(Long.valueOf(value));
		}else if(t instanceof Long){
			t=(T) Long.valueOf(value);
		}else if(t instanceof Date){
		    t=(T) DateTimeHelper.parse(value);
		}else if(t instanceof Timestamp){
			t=(T) Timestamp.valueOf(value);
		}else if(t instanceof Boolean){
			if(value.equalsIgnoreCase("Y")){
				t=(T)Boolean.TRUE;
			}else{
			    t=(T)Boolean.valueOf(value);
			}
		}else if(t instanceof Byte[]){
			t=(T)value.getBytes();
		}
		}
		catch (Exception e) {
			t=defaultValue;
		}
		return t;
    }
    
    
   
	public  static <T> T parse(String value,T defaultValue,Class<T> cls){
		T t=defaultValue;
		if(value==null){
			return t;
		}
		try{
		if(String.class.equals(cls)){
			t=(T) value;
		}else if(Character.class.equals(cls)&&value.length()>0){
			t=(T)Character.valueOf(value.charAt(0));
		}
		else if(Integer.class.equals(cls)){
			t=(T) Integer.valueOf(value);
		}else if(Double.class.equals(cls)){
			t=(T) Double.valueOf(value);
		}else if(Float.class.equals(cls)){
			t=(T) Float.valueOf(value);
		}else if(BigDecimal.class.equals(cls)){
			t=(T) BigDecimal.valueOf(Long.valueOf(value));
		}else if(Long.class.equals(cls)){
			t=(T) Long.valueOf(value.toString());
		}else if(Date.class.equals(cls)){
		    t=(T) DateTimeHelper.parse(value);
		}else if(Timestamp.class.equals(cls)){
			t=(T) Timestamp.valueOf(value);
		}else if(Boolean.class.equals(cls)){
			if(value.equalsIgnoreCase("Y")){
				t=(T)Boolean.TRUE;
			}else{
			    t=(T)Boolean.valueOf(value);
			}
		}else if(t instanceof Byte[]){
			t=(T)value.getBytes();
		}
		}
		catch (Exception e) {
			t=defaultValue;
		}
		return t;
    }

	
	
	
	
	
	
	
	@SuppressWarnings("all")
    public static boolean isEmpty(Object o)  {
        if(o == null) return true;

        if(o instanceof String) {
          
          return StringHelper.isBlank((String)o);
            
        } else if(o instanceof Collection) {
            return ((Collection)o).isEmpty();
        } else if(o.getClass().isArray()) {
            if(Array.getLength(o) == 0){
                return true;
            }
        } else if(o instanceof Map ) {
            return ((Map)o).isEmpty();
        }
        else {
            return false;
        }

        return false;
    }
	
	public static boolean isNotEmpty(Object o)
	{
		return !isEmpty(o);
	}
	
	@SuppressWarnings("all")
	public static Map toMap(Object[] array,String...keys) {
		if(array == null) return new HashMap();
		Map m = new LinkedHashMap();
		for(int i = 0; i < keys.length; i++) {
			if(array.length == i ) {
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
	
	public static <T> List<T> toList(Map<Object,T> map ){
		List<T> listReturn=new ArrayList();
		for(Iterator<Object> it=map.keySet().iterator();it.hasNext();)
		{
			Object key=it.next();
			listReturn.add(map.get(key));
		}
		return listReturn;
		
	}
	
	
	public static <T> List<T> toList(T...ts){
		List<T> list=new ArrayList<T>();
		for(T t: ts){
			list.add(t);
		}
		return list;
	}
	
	public static <T> Set<T> toSet(T...ts){
		return new HashSet<T>(toList(ts));
	}
	
	public static Map<String,Object> toMap(Object obj){
		Map<String,Object> map=new HashMap<String, Object>();
		if(obj==null){
			return map;
		}else if(obj instanceof Map){
			try{
			  map=(Map<String, Object>) obj;
			}catch (Exception e) {
			  return map;
			}
			return map;
		}
		Class[] lAllClass=getAllClass(obj.getClass());
		for (Class c : lAllClass) {
		   
			Field[] lArrField=c.getDeclaredFields();
			
			for (int i=0;i<lArrField.length;i++) {
				String lStrFieldName=lArrField[i].getName();
                try{
                Object lObjFieldValue=attr(obj,lStrFieldName);
                if(lObjFieldValue==null)continue;
                map.put(lStrFieldName,lObjFieldValue);
               }catch(Exception ex){}
			}
		}

		return map;
	}
	
	
	
	
	
	
	public static  boolean isEquals(Object obj1,Object obj2){
		if(obj1!=null&&obj2!=null){
			return obj1.equals(obj2);
		}
		return obj1==null&&obj2==null;
	}
	
	public static <T> boolean isNotEquals(Object obj1,Object obj2){
		return !isEquals(obj1, obj2);
	}
	
	public static <T> boolean isIn(T obj1,T... objs){
		for(T obj:objs){
			if(isEquals(obj1, obj))return true;
		}
		return false;
	}
	
	
	
	public static <T> boolean isNotIn(T obj1,T... objs){
		return !isIn(obj1,objs);
	}
	
	public static <T> Class<T> actualTypeBy(Class cls,int index){

	    return actualTypeBy(cls, index,0);
		  
	}
	
	public static <T> Class<T> actualTypeBy(Class cls,int index,int interfaceIndex){
		ParameterizedType parameterizedType=null;
		if(cls.isInterface()){
			parameterizedType=(ParameterizedType)cls.getGenericInterfaces()[interfaceIndex];
	    }else{
		    parameterizedType=(ParameterizedType) cls.getGenericSuperclass();
	    }
		Class<T> clz=(Class<T>) parameterizedType.getActualTypeArguments()[index];
	    return clz;
		  
	}
	
	public static <A extends Annotation> A annotationBy (Class cls,Class<A> annCls) throws NullPointerException{
		Class superCls=cls;
		A ann=(A) superCls.getAnnotation(annCls);
		
		while(ann==null&&!Object.class.equals(superCls)){
			//throw new NullPointerException("Annotation is not exist");
			try{
			superCls=superCls.getSuperclass();
			ann=(A) superCls.getAnnotation(annCls);
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		return ann;
	}
	
	public static Type getTypez(Type type,int index){
		   if (type instanceof Class<?>) {
			      return Object.class;
		} else if (type instanceof ParameterizedType) {
			      return ((ParameterizedType)type).getActualTypeArguments()[index];
		} else if (type instanceof GenericArrayType) {
			      return getTypez(((GenericArrayType)type).getGenericComponentType(),index);
		} else {
			      throw new IllegalArgumentException("Type \'" + type + "\' is not a Class, "
			          + "ParameterizedType, or GenericArrayType. Can't extract class.");
		}
	}
	
	

	
	
	
	
	

	
	
	
	


	private static Map<Class,Class[]> CACHE_CLASSS=new HashMap<Class, Class[]>();
	private static Map<Class,Field[]> CACHE_FIELDS=new HashMap<Class, Field[]>();
	private static Map<Class,Map<String,Method>> CACHE_SETTERS=new HashMap<Class,Map<String,Method>>()
			,CACHE_GETTERS=new HashMap<Class,Map<String,Method>>()
			;
	
	public static Class[] getAllClass(Class c){
		if(CACHE_CLASSS.containsKey(c))return CACHE_CLASSS.get(c);
		List<Class> lClassReturn=new ArrayList<Class>();
		lClassReturn.add(c);
		if(!c.getSuperclass().equals(Object.class)){
			
			for(Class cls : ObjectHelper.getAllClass(c)){
				lClassReturn.add(cls);
			}
		}	
		Class[] cs= lClassReturn.toArray(new Class[lClassReturn.size()]);
		CACHE_CLASSS.put(c,	cs);
		return cs;
	}
	
	public static Field[] allDeclaredFieldsBy(Class c){
		if(CACHE_FIELDS.containsKey(c))return CACHE_FIELDS.get(c);
		List<Field> fields=new ArrayList<Field>();
		for(Class cls : ObjectHelper.getAllClass(c)){
			for(Field field :cls.getDeclaredFields()){
                                if(isThis0(field))continue;
				fields.add(field);
			}
		}
		Field[] fs= fields.toArray(new Field[fields.size()]);
		CACHE_FIELDS.put(c, fs);
		return fs;
	}
	
	public static Set<Method> getAllDeclaredMethods(Class c){
		Set<Method> methods=new HashSet<Method>();
		for(Class cls : ObjectHelper.getAllClass(c)){
			for(Method method :cls.getDeclaredMethods()){
				methods.add(method);
				
			}
		}
		return methods;
	}
	
	

	public static Method setterBy(Class c,Field field) throws NoSuchMethodException, SecurityException{
		
	    return settersBy(c).get(field.getName());
	}

	public static Method getterBy(Class c,Field field) throws NoSuchMethodException, SecurityException{
		
	    return gettersBy(c).get(field.getName());
	}
	
	
	
	public static Map<String,Method> settersBy(Class c) throws NoSuchMethodException, SecurityException{
		if(CACHE_SETTERS.containsKey(c))return CACHE_SETTERS.get(c);
		Map<String,Method> setters=new HashMap<String,Method>();
		for(Field field : ObjectHelper.allDeclaredFieldsBy(c)){
		String settername="set"+field.getName().substring(0,1).toUpperCase()+field.getName().substring(1);
	      setters.put(field.getName(), c.getMethod(settername,field.getType()));
		}
		CACHE_SETTERS.put(c, setters);
		return setters;
	}

	public static Map<String,Method> gettersBy(Class c) throws NoSuchMethodException, SecurityException{
		if(CACHE_GETTERS.containsKey(c))return CACHE_GETTERS.get(c);
		Map<String,Method> getters=new HashMap<String,Method>();
		for(Field field : ObjectHelper.allDeclaredFieldsBy(c)){
		String settername="get"+field.getName().substring(0,1).toUpperCase()+field.getName().substring(1);
		 getters.put(field.getName(), c.getMethod(settername));
		}
		CACHE_GETTERS.put(c, getters);
		return getters;
	}
	

	public static <T> Object attr(Object obj, String pattern, T value) throws Exception {
		// TODO Auto-generated method stub
		if(obj instanceof Map){
			((Map) obj).put(pattern, value);
			return obj;
		}
		boolean isOk=false;
		Class[] lAllClass=getAllClass(obj.getClass());
		String lTempName="set"+StringHelper.format(pattern, StringHelper.Format.upcaseFirstChar);
		for (Class c : lAllClass) {
			try{
			Field f=c.getDeclaredField(pattern);
			Type t=f.getType();
			Method lMth=c.getDeclaredMethod(lTempName, f.getType());
			if(value instanceof String && !t.equals(String.class)){
				lMth.invoke(obj, ObjectHelper.parse(value.toString(), null,f.getType()));
			}else{
			lMth.invoke(obj,value);
			}
			if(lMth!=null){isOk=true;break;}
			}catch(Exception ex){}
		}
		if(!isOk){ throw new Exception(MessageFormat.format("This Model have not \"{0}\" setter({1})", pattern,obj.getClass().getName()));}
	    return obj;
	}

	public static <T> T attr(Object obj, String pattern) throws Exception{
		// TODO Auto-generated method stub
		if(obj instanceof Map){
			return (T) ((Map)obj).get(pattern);
		}
		boolean isOk=false;
		Class[] lAllClass=getAllClass(obj.getClass());
		String lTempName="get"+StringHelper.format(pattern, StringHelper.Format.upcaseFirstChar);
		T lObjFieldValue=null;
		for (Class c : lAllClass) {
			try{
			Method lMth=c.getDeclaredMethod(lTempName, null);
			if(lMth==null){continue;}
			lObjFieldValue=(T) lMth.invoke(obj, null);
			isOk=true;
			}catch(Exception ex){}
		}
		if(!isOk){ throw new Exception(MessageFormat.format("This Model have not \"{0}\" 's getter", pattern));}
		return lObjFieldValue;
	}
	
	public static <T> void cp(T source, T target) throws Exception{
		// TODO Auto-generated method stub
	       if(source==null||target==null)throw new NullPointerException("source or target can't be null");
               Map<String,Method> getters=gettersBy(source.getClass())
                       ,setters=settersBy(source.getClass())
               ;
               for (Map.Entry<String, Method> entrySet : getters.entrySet()) {
                String key = entrySet.getKey();
                Method getter = entrySet.getValue(),setter=setters.get(key);
                if(getter==null||setter==null)continue;
                Object val=getter.invoke(source);
                if(val==null)continue;
                setter.invoke(target, val);
            }
        }

	

	@Deprecated
	public synchronized static String GeneralPK() throws NullPointerException,InterruptedException{
		String pk="";
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmssSSS");
		Random r=new Random();
		int randomInt=r.nextInt(10000);
		//String code=String.valueOf(randomInt);
		//while(code.length()<4){
		//	code="0"+code;
		//}
		pk=sdf.format(new Date());
	    Thread.sleep(1);
		return pk;
	}
	

 
	
	








	




	public static Type fieldType(Object obj,String pattern) throws Exception {
		// TODO Auto-generated method stub
		boolean isOk=false;
		Class[] lAllClass=getAllClass(obj.getClass());
		Type t=null;
		//String lTempName="set"+StringHelper.format(pattern, EStringFormat.upcaseFirstChar);
		for (Class c : lAllClass) {
			try{
			Field f=c.getDeclaredField(pattern);
			 if(f!=null){
				 t=f.getType();
				 isOk=true;
				 break;
			 }                   
			}catch(Exception ex){}
		}
		if(!isOk){ throw new Exception(MessageFormat.format("This Model have not \"{0}\" ", pattern));}
        
		return t;	
	}


        public static boolean isThis0(Field field){
           return field.getName().equals("this$0");
        }



	





	



   






 

  
	
	
	
	
	
	
	
}

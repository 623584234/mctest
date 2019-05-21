package com.mc.util;

import java.util.Map;

public class MapTransformUtils {
	
	public static <T> T mapToObject(Map<String, String> map, Class<T> beanClass) throws Exception {    
        if (map == null) {
            return null;
        }
 
        T obj = beanClass.newInstance();  
        org.apache.commons.beanutils.BeanUtils.populate(obj, map);  
 
        return obj;
    }    
 
    /**
     * 
    * @Title: objectToMap
    * @Description: TODO(bean转换为Map)
    * @return Map<?,?>    返回类型
    * @param obj
    * @return
     */
    public static Map<?, ?> objectToMap(Object obj) {  
        if(obj == null) {
             return null;   
        }
        return new org.apache.commons.beanutils.BeanMap(obj);  
    }
}

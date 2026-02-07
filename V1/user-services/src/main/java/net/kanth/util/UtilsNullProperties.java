package net.kanth.util;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class UtilsNullProperties {
	 public static String[] getNullPropertyNames(Object source) {
	        final BeanWrapper src = new BeanWrapperImpl(source);
	        PropertyDescriptor[] pds = src.getPropertyDescriptors();

	        Set<String> emptyNames = new HashSet<>();
	        for (PropertyDescriptor pd : pds) {
	            Object srcValue = src.getPropertyValue(pd.getName());
	            if (srcValue == null) {
	                emptyNames.add(pd.getName());
	            }
	        }
	        return emptyNames.toArray(new String[0]);
	    }
}

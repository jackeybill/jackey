package com.tf.test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Map;

public class GenericFoo<T extends List<Map>> {
	private String str;
	private T obj;
	
	public void setObj(T obj){
		this.obj = obj;
	}
	
	public T getObj(){
		return this.obj;
	}
	
	public String getStr(){
		return this.str;
	}

	/**
	 * @param args
	 * @throws NoSuchFieldException 
	 * @throws SecurityException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public static void main(String[] args) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		// TODO Auto-generated method stub
		//GenericFoo<?> gf = new GenericFoo<new ArrayList<Hashtable>>();
		
		Class<?> claZZ = GenericFoo.class;
		GenericFoo foo = new GenericFoo();
		// private method/properties also can be called/modified
		Field field =  claZZ.getDeclaredField("str"); //claZZ.getField("obj");
		field.setAccessible(true);
		field.set(foo, "test");
		
		// dynamic proxy must return a interface, not be a real class
		InvocationHandler ds = new DynamicSubject(rs);
		GenericFoo gfProxy = Proxy.newProxyInstance(foo.getClass().getClassLoader(), foo.getClass().getInterfaces(), foo);
		
		System.out.println( foo.getStr() );
		
		//field.setAccessible(arg0, arg1)
		// BootStrap --> Ext-ClassLoader --> Application-ClassLoader
		
		// struts-default.xml / struts-plugin.xml / struts.xml
		
		//

	}

}

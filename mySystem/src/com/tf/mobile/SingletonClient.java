package com.tf.mobile;

import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import cn.emay.sdk.client.api.Client;


public class SingletonClient {
	private static Client client=null;
	private static ResourceBundle bundle=null;
	public static boolean isRun = false;
	public static String currentRole = null;
	private SingletonClient(){
	}
	public synchronized static Client getClient(String softwareSerialNo,String key){
		if(client==null){
			try {
				client=new Client(softwareSerialNo,key);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return client;
	}
	public synchronized static Client getClient(){
		bundle=PropertyResourceBundle.getBundle("config",Locale.CHINESE);
		if(client==null){
			try {
				client=new Client(bundle.getString("softwareSerialNo"),bundle.getString("key"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return client;
	}
	
	public synchronized static String getKey(String key){
		bundle=PropertyResourceBundle.getBundle("config");
		return bundle.getString(key);
	}
	public static void main(String str[]){
		//SingletonClient.getClient();
	}
	
}

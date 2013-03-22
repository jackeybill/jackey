package com.tf.mobile;

import cn.emay.sdk.client.api.Client;

public class SendMsg {

	// 3SDK-VBF-0130-LITNM/339306 3SDK-VBF-0130-LITNN/911488	
	public static String softwareSerialNo="3SDK-VBF-0130-LITNN";
	public static String key="XXXXXX";
	public static String password="911488";
	public static void init() throws Exception{
		
	}
	
	public static void main(String[] args) {
		try {		
			//registEx();
			
			Client client2=new Client(softwareSerialNo,password);
			
			//client2.registEx(password);
			
			/*int i=client2.sendSMS(new String[] {"13918221701","18918693073"}, "发送到家,魅力服务在我家[浦东嘉里城]","110",3);
			System.out.println("testSendSMS.sendSMS()====="+i);*/
			
			double a=client2.getBalance();
			System.out.println("testSendSMS。getBalance====="+a);
			
			//int a = client2.logout();
			//System.out.println("testLogout:" + a);			
			/*Thread thread = new LoadFileTask();
			thread.start();*/
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	
	public static void registEx() {
		int i = SingletonClient.getClient().registEx(password);
		System.out.println("registEx:" + i);
	}
}

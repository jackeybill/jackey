package com.tf.mobile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.huawei.eie.api.sm.DBSMProxy;
import com.huawei.eie.api.sm.SmReceiveBean;
import com.huawei.eie.api.sm.SmSendBean;
import com.huawei.eie.api.sm.SmSendResultBean;

public class DBSMProxyTest {
	private String username=null;
	private String pwd=null;
	private DBSMProxy smproxy = null;
		
	public DBSMProxyTest() {
		username = SingletonClient.getKey("username");
		pwd = SingletonClient.getKey("pwd");
	}

	/**
	 * ÑÝÊ¾ÈçºÎÊ¹ÓÃdbsmproxy ´úÂëÑùÀý¡£
	 * 
	 * @throws Exception
	 */
	public void sendMessage() throws Exception {
		//testSend(smproxy, 100); // ·¢ËÍÏûÏ¢²âÊÔ½Ó¿Ú¡£
		//testGetReceivedSm(smproxy, 10); // ½ÓÊÕÏûÏ¢²âÊÔ½Ó¿Ú¡£
		//testQuerysmResult();// ²éÑ¯ÏûÏ¢²âÊÔ½Ó¿Ú¡£
	}
	
	public void logOut() throws Exception {
		// ÍË³öµÇÂ½¡£
		smproxy.logout();

		// Ïú»ÙÁ¬½Ó¡£
		smproxy.destroy();
	}

	/**
	 * ÑÝÊ¾ÈçºÎ²úÉúÒ»¸öproxyÁ¬½ÓµÄ´úÂëÑùÀý
	 * 
	 * @return DBSMProxy
	 * @throws Exception
	 */
	private DBSMProxy createProxy() throws Exception {
		smproxy = new DBSMProxy();
		try {
			smproxy.initConn("smApiConf.xml");// ×Ô¶¯²éÕÒÅäÖÃÎÄ¼þµÄ·½Ê½³õÊ¼»¯£¬²Î¼ûÉÏÃæº¯ÊýËµÃ÷¡£
		} catch (Exception ex) {
		}
		// ÊÖ¶¯²éÕÒÅäÖÃÎÄ¼þµÄ·½Ê½£¬²Î¼ûÉÏÃæº¯ÊýËµÃ÷¡£
		// smproxy.initConn("./apiconf/smapiconf.xml");

		// µÇÂ½ÆóÒµÐÅÏ¢»úmas¶ÌÐÅÓ¦ÓÃ½Ó¿Ú£¬ÒµÎñµÇÂ½ÓÃ»§Ãû¡¢ÒµÎñµÇÂ½ÃÜÂë¡£ " Y4yhl9t!");
		try {
			smproxy.login("jackey", "Y4yhl9t!");
		} catch (Exception ex) {
			ex.printStackTrace();
			smproxy.login("admin(0000)", "Y4yhl9t!");
		}
		return smproxy;
	}

	/**
	 * ²éÑ¯ÏûÏ¢·¢ËÍ½á¹û£¬´úÂëÑùÀý¡£
	 * 
	 * @param smproxy
	 *            DBSMProxy
	 * @throws Exception
	 */
	public void getSMSResult()
			throws Exception {
		// System.out.println(">>>>>>>>>>::::::::::::");
		// ¹¹Ôì²éÑ¯Ìõ¼þ£¬Èë¿Ú²ÎÊýÏêÏ¸ËµÃ÷²Î¼ûÉÏÃæº¯ÊýËµÃ÷¡£
		SmSendResultBean[] beans = smproxy
				.querySmsResult(1, new java.util.Date(System
						.currentTimeMillis() - 864000), new java.util.Date(
						System.currentTimeMillis()), null,null);
		for (SmSendResultBean bean : beans) {
			//bean.get
			System.out.println("bean:" + bean.getSmMsgContent());
		}
	}

	/**
	 * ·¢ËÍ²âÊÔ´úÂëÑùÀý¡£
	 * 
	 * @param smproxy
	 *            DBSMProxy
	 * @param count
	 *            int
	 * @throws Exception
	 */
	public int[] sendSMS(String[] addrs,String msg)
			throws Exception {
		createProxy();
		
		SmSendBean bean = new SmSendBean();
		bean.setSmDestAddrs(addrs);
		// ¹¹ÔìÏûÏ¢ÄÚÈÝ¡£"http://www.126.com,sms content test info...~!@#$%^&*()_+|.ÖÐÎÄ²âÊÔ'sdfadsfa''."
		bean.setSmMsgContent(msg);
		// ·¢ËÍ³öÈ¥¡£
		bean.setPriority(1);
		bean.setSendType(SmSendBean.SMSEND_TYPE_TEXT);
		int[] ret = smproxy.sendSm(bean);
		
		//logOut();
		
		return ret;
	}

	/**
	 * ²âÊÔ·½·¨Ö÷Èë¿Ú¡£
	 * 
	 * @param args
	 *            String[]
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		//testSendMessage();
		DBSMProxyTest proxy = new DBSMProxyTest();
		proxy.sendSMS(new String[]{"18821194446","13701663364"}, "短信测试，收到请电话给曾明：18918693073");
	}
}

/*
 * LoadFileTask.java
 *
 * Created on 21 March 2007, 19:55
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.tf.mobile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Vector;

import org.apache.log4j.Logger;

/**
 * 
 * @author aMeng
 */
public class LoadFileTask extends Thread {
	private static final Logger log = Logger.getLogger(LoadFileTask.class);
	String curTime = "2000-01-06 18:11:09";
	String curMsg = null;
	String refeashTime = null;
	String warnKeys = null;
	String smsProvider = "VBF";
	// String warnContacts = null;
	int maxLen = 30000;
	int waitTime = 10000; // 10s;
	static int iMaxWarn = 20;
	int maxWarn = iMaxWarn;

	void getDBMsg() throws SQLException {
		/*
		 * 
F100513	Discipline	The text of the discipline	string50	NULL	0	1601 火警
F300102	Event time	Time Stamp of the event generation	filetime	NULL	0	1614 1ce1c97 62969c00
F300114	Short location	Location from the source point up to the application node type	memo	NULL	0	1652  1-5F探测 1801/1 . 东风G层 . 烧烤间内-4烟感L1D3	2
F300401	Location	Source Location (BUILDING1.AREA27.ZONE35)	memo	NULL	0	1631

1740	1033	火警	探测	6	警报	来源状态改变	117	117	1ce1c97 62969c00	1	未处理	字段	火灾预警	NULL	1	NULL	NULL	现场 . CS11EP7 #1 . CC11EP7 #1 . 1-5F探测 1801/1 . 东风G层 . 烧烤间内-4烟感L1D3	S1_P1.S1_P3.S25_P1.S26_P1.S26_P3.S26_P41421.S26_P197493.S26_P202113	1	2	1739	现场 . CS11EP7 #1 . CC11EP7 #1 . 1-5F探测 1801/1 . 东风G层 . 烧烤间内-4烟感L1D3	2	运行模式	火灾预警	未确认	01/001/081/000
		 * */
		String sql = "select RegistrationKey,F100513,F300102,F300114,F300401 from T1_1";
		Vector<Vector<Object>> result = MSSqlUtil.getTable(sql);
		for(Vector<Object> res:result){
			for(Object obj:res){
				//System.out.println(obj==null?"":obj .toString()+"|");
				log.info(obj);
			}
			Clob clob = (Clob)res.get(4);
			//System.out.println( clob.getSubString(1,1000));
			log.info(clob.getSubString(1,1000));
		}
	}
	
	void getUpdatedMsg() {
		ByteArrayOutputStream bo = null;
		RandomAccessFile raf = null;
		// String filename="C:/jackey/Java/data0106/REC20120106.DB";
		String filename = "C:/jackey/Java/data0106/message.db";

		try {
			filename = SingletonClient.getKey("dbfilename");
			log.debug("filename=" + filename);
			File inputFile = new File(filename);
			raf = new RandomAccessFile(inputFile, "r");
			bo = new ByteArrayOutputStream();

			int len = (int) raf.length();
			log.info("---len:" + len + ";curTime:" + curTime);
			if (len > maxLen) {
				int v = (int) (len - maxLen);
				raf.seek(v);
			}

			byte[] b = new byte[4096];
			int count = 0;
			while ((count = raf.read(b)) != -1) {
				bo.write(b, 0, count);
			}
			byte[] bb = bo.toByteArray();
			// log.debug(new String(bb));
			/*
			 * for(int i=0;i<bb.length;i++){ System.out.println("--"+bb[i]); }
			 */
			byte[] bbs = null;
			len = bb.length;

			int iPos = 0, i = 0;
			String desc = null, action = null, strTime = null;
			while (iPos != -1) {
				if (i == len)
					break;
				if (bb[i++] != 2) {
					continue;
				} else {
					bb = Arrays.copyOfRange(bb, i + 1, len);

					len = bb.length;
					i = 0;

					if (bb[0] != 50)
						continue;

					// System.out.println("--bb-"+new String(bb,"GBK"));
					bbs = Arrays.copyOfRange(bb, 0, 19);
					strTime = new String(bbs);
					log.debug("strTime=" + strTime);
					if (strTime.compareTo(curTime) >= 0) {
						bbs = Arrays.copyOfRange(bb, 40, 48);
						action = new String(bbs);
						// log.info( "action=" + new String(bbs) );
						int j = 137;
						for (; j < len; j++) {
							if (bb[j] == 0)
								break;
						}
						bbs = Arrays.copyOfRange(bb, 137, j);
						// desc = new String(bbs,"GBK");
						desc = new String(bbs);

						curTime = strTime;
						String msg = "time:" + strTime + ":description:" + desc
								+ ":action:" + action;
						log.info(msg);
						// log.info( new String(msg.getBytes(),"UTF-8") );
						if (warnKeys.contains(action) && !desc.equals(curMsg)) {
							sendWarnsMsg(strTime, action, desc);
							curMsg = desc;
							log.debug("curMsg=" + curMsg);
						}
					}
				}
			}
		} catch (OutOfMemoryError ex) {
			log.error(ex);
		} catch (FileNotFoundException ex) {
			log.error(ex);
		} catch (Exception ex) {
			log.error(ex);
		} finally {
			try {
				bo.close();
			} catch (Exception ex) {
			}
			try {
				raf.close();
			} catch (Exception ex) {
			}
		}
	}

	@Override
	public void run() {
		int iWarn = 0;
		warnKeys = getWarnKeys();
		while (true) {
			try {
				if (SingletonClient.isRun) {
					//TODO chang to use the db listener
					//getUpdatedMsg();
					getDBMsg();
					if (curTime.equals(refeashTime)) {
						iWarn++;
						/*log.warn("Can't read the log file. MaxTimes=" + maxWarn
								+ ", Now times=" + iWarn);*/
					} else {
						refeashTime = curTime;
						iWarn = 0;
						maxWarn = iMaxWarn;
					}
					if (iWarn > maxWarn) {
						// warnMsg
						//sendSMS(curTime, "WARN", "error when open CRT_PRO","warnMsg");
						maxWarn = maxWarn * 20;
					}
					//sendPendingSMS();
				}
				sleep(waitTime);
			} catch (InterruptedException e) {
				log.error(e);
			} catch (SQLException e) {
				log.error(e);
			}
		}
	}

	void sendWarnsMsg(String strTime, String strAct, String strDesc) {
		String strLocation = null;
		try {
			log.debug("strDesc=" + strDesc);
			int iBuilding = strDesc.indexOf(" ");
			if (iBuilding > 0) {
				strLocation = strDesc.substring(0, iBuilding);
				log.debug("strLocation=" + strLocation);
				sendSMS(strTime, strAct, strDesc, strLocation);
			}
		} catch (Exception e) {
			log.error(e);
		}
	}

	void sendSMS(String strTime, String strAct, String strDesc, String key) {
		String msg = null;
		// int i=-1;
		try {
			log.debug("key=" + key);
			String mobileLists = getMobileList(key);
			// String[] strArrMobileNo = mobileLists.split(",");
			// {time}-{description}[嘉里中心]
			msg = getTemplete(key);
			// msg = "<time>-<description>[嘉里中心]";
			msg = msg.replaceAll("<time>", strTime);
			msg = msg.replaceAll("<description>", strDesc);
			log.debug(msg);
			// i = SingletonClient.getClient().sendSMS(strArrMobileNo,
			// msg,"110",3);
			saveSMS(-1, mobileLists, msg);
			log.info("mobileLists=" + mobileLists + ";===msg=" + msg);
		} catch (Exception e) {
			log.error(e);
		}
	}

	private void sendPendingSMS() {
		int iRC = -1;

		String sql = "select id,sendto,sendmsg from mo where isSend='0' and sendtime<now()";
		Object[][] records = USeDB.getData(sql);
		for (int i = 0; i < records.length; i++) {
			// for(int j=0;j<records[i].length;j++){
			Integer iID = (Integer) records[i][0];
			String sendto = (String) records[i][1];
			String sendmsg = (String) records[i][2];

			iRC = sendoutSMS(sendto.split(","), sendmsg);
			log.debug("sendPendingSMS()---sendto=" + sendto + ";strID=" + iID
					+ ";sendmsg=" + sendmsg + ";iRC=" + iRC);
			if (iRC == 0) {
				sql = "update mo set isSend='1',returnCode='0' where id=" + iID;
				log.debug(sql);
				USeDB.UpdateDB(sql);
			}
			// }
		}
	}

	private int sendoutSMS(String[] sendto, String sendmsg) {
		int iReturn = -1;

		try {
			smsProvider = SingletonClient.getKey("smsProvider");
			if ("VBF".equals(smsProvider)) {
				iReturn = SingletonClient.getClient().sendSMS(sendto, sendmsg,
						"110", 3);
			} else {
				DBSMProxyTest proxy = new DBSMProxyTest();

				proxy.sendSMS(sendto, sendmsg);
				iReturn = 0;
			}
		} catch (Exception e) {
			log.error(e);		
		}
		return iReturn;
	}

	private void saveSMS(int returnCode, String sendTo, String sendMsg) {
		String sql = null;
		try {
			sql = "'" + sendTo + "',now(),'" + sendMsg + "','"
					+ (returnCode == 0 ? "1" : "0") + "','" + returnCode + "'";
			sql = "insert into mo(sendTo,sendTime,sendMsg,isSend,returnCode) values("
					+ sql + ")";
			log.debug(sql);
			USeDB.UpdateDB(sql);
		} catch (Exception e) {
			log.error(e);
		}
	}

	private String getTemplete(String key) {
		String record = null;
		try {
			log.debug("key=" + key);
			record = getTempleteFromDB(key);
			if (record == null)
				record = getTempleteFromDB(getBuilding(key));
			if (record == null)
				record = getTempleteFromDB("default");
			log.debug("val=" + record);
		} catch (Exception e) {
			log.error(e);
		}
		return record;
	}

	private String getTempleteFromDB(String key) {
		String record = null;
		try {
			log.debug("key=" + key);
			String sql = "select templete from mo_templete where keyName='"
					+ key + "'";
			log.debug("sql=" + sql);
			record = DBUtil.getOneFieldValue(sql);
			log.debug("val=" + record);
		} catch (Exception e) {
			log.error(e);
		}
		return record;
	}

	private String getWarnKeys() {
		String record = null;
		try {
			// log.info("key="+key);
			String sql = "select keyvalue from configuration where keyName='warnKey'";
			log.debug("sql=" + sql);
			record = DBUtil.getOneFieldValue(sql);
		} catch (Exception e) {
			log.error(e);
		}
		return record;
	}

	private String getMobileList(String key) {
		String record = null;
		try {
			log.debug("key=" + key);
			record = getMobileFromDB(key);
			if (record == null)
				record = getMobileFromDB(getBuilding(key));
			if (record == null)
				record = getMobileFromDB("default");
			log.debug("val=" + record);
		} catch (Exception e) {
			log.error(e);
		}
		return record;
	}

	private String getMobileFromDB(String key) {
		String record = null;
		try {
			log.debug("key=" + key);
			String sql = "select keyValues from mobile_lib where keyName='"
					+ key + "'";
			log.debug("sql=" + sql);
			record = DBUtil.getOneFieldValue(sql);
			log.debug("val=" + record);
		} catch (Exception e) {
			log.error(e);
		}
		return record;
	}

	private String getDetailInfo(String key, boolean isFloor) {
		String building = null, floor = null;
		try {
			int iFloor = key.indexOf("层");

			if (iFloor > 0 && iFloor < 3) {
				// 6层南裙房,6层东裙房(北),5层东裙房夹层,4层北裙房及酒店,4层办公楼及南裙房,2层公寓,2层办公楼及南裙房,1层公寓,
				// B2层3区
				floor = key.substring(0, iFloor);
				building = key.substring(iFloor + 1);
			} else if (iFloor > 3) {
				// 办公楼机电层,办公楼R2层
				// 公寓机电层,公寓主屋顶层,公寓9层
				// 酒店主屋顶层,酒店中间机电层,酒店R2层
				int iBuilding = 2;
				if (key.startsWith("办公楼")) {
					iBuilding = 3;
				}
				building = key.substring(0, iBuilding);
				floor = key.substring(iBuilding, iFloor);
			}
		} catch (Exception e) {
			log.error(e);
		}
		return isFloor ? floor : building;
	}

	private String getFloor(String key) {
		return getDetailInfo(key, true);
	}

	private String getBuilding(String key) {
		return getDetailInfo(key, false);
	}

}

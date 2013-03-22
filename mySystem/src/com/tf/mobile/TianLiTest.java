package com.tf.mobile;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.huawei.eie.api.sm.DBSMProxy;
import com.huawei.eie.api.sm.SmReceiveBean;
import com.huawei.eie.api.sm.SmSendResultBean;
import com.huawei.eie.api.sm.SmSendBean;

/**
 * <p>Title: mas</p>
 *
 * <p>Description: masproject</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: www.huawei.com</p>
 *
 * @author www.huawei
 * @version 1.0
 */
public class TianLiTest
{
  private static long SendTime = 0;
    private static long ReceiveTime = 0;
    public static FileWriter fw = null;
    public static PrintWriter out = null;


    /** Creates a new instance of Main */
    public TianLiTest() {
    }

    public static final void testSendMessage() throws Exception
    {
        DBSMProxy smproxy = createProxy(null);

//        testSend(smproxy,50001); //发送消息测试接口。
        testGetReceivedSm(smproxy,40000); //接收消息测试接口。
//        testQuerysmResult(smproxy,40000);//查询消息测试接口。

        //退出登陆。
        smproxy.logout();

        //销毁连接。
        smproxy.destroy();
     }

     public static final DBSMProxy createProxy(DBSMProxy smproxy) throws Exception
     {
       if (smproxy == null)
       {
         smproxy = new DBSMProxy();
       }
            smproxy.initConn("smApiConf.xml");
        try
        {
            smproxy.login("admin","Mas12345*");
        }
        catch (Exception ex)
        {
            smproxy.login("admin","Mas12345*");
            ex.printStackTrace();
        }
        return smproxy;
     }

     /**
     * 查询上行消息代码样例。
     * @param smproxy DBSMProxy
     * @param count int
     * @throws Exception
     */
     public static final void testGetReceivedSm(DBSMProxy smproxy, int count) throws Exception
     {
        SmReceiveBean[] beans = null;
        //循环接收测试短信
        System.out.println("===========Receive Start==========");
        long firstTime = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int index = 0; index < count; index++)
        {
             beans = smproxy.getReceivedSms(2,null,null,null,null);
            
             if (beans.length != 0)
             {
                 System.out.println("get received sms from MAS,SourceAddr = "+ beans[0].getSmOrgAddr() +", MsgContent = "+beans[0].getSmMsgContent());
                 System.out.println("输出时间: "+sdf.format(beans[0].getSmRecvTime()));
                 beans[0] = null;
             }
        }

        long lastTime = System.currentTimeMillis();
        ReceiveTime = lastTime - firstTime;
        System.out.println(" Received:" + count + " records,consumed " + (lastTime - firstTime) + " ms");

        System.out.println("===========Receive End==========");
     }

  /**
   * 查询消息发送结果，代码样例。
   *
   * @param smproxy DBSMProxy
   * @param count int
   * @throws Exception
   */
  public static final void testQuerysmResult(DBSMProxy smproxy,int count) throws Exception
      {
        //构造查询条件，入口参数详细说明参见上面函数说明。
        System.out.println("=======query start========"+new java.util.Date());
        long firstTime = System.currentTimeMillis();
        for (int index = 0; index < count ; index++)
        {
          SmSendResultBean[] beans =
              smproxy.querySmsResult( -1
              , new java.util.Date(System.currentTimeMillis() - 86400000L)
              , new java.util.Date(System.currentTimeMillis())
              , "1860", "1399999");
          for (SmSendResultBean bean : beans)
          {
            System.out.println("bean:" + bean.getSmMsgContent());
          }
        }
        long lastTime = System.currentTimeMillis();
        ReceiveTime = lastTime - firstTime;
        System.out.println(" query:" + count + " times,consumed " + (lastTime - firstTime) + " ms");
        System.out.println("query end"+new java.util.Date());
      }

      /**
       * 发送测试代码样例。
       * @param smproxy DBSMProxy
       * @param count int
       * @throws Exception
      */
      public static final void testSend(DBSMProxy smproxy, int count)throws Exception
      {
         SmSendBean bean = new SmSendBean();
         //ArrayList<String> arrs = new ArrayList();
         String[] addrs = new String[1];
         addrs[0] = "13901001922";
         int[] ret = new int[1];
         //循环发送测试短信
         System.out.println("===========Send Start==========");
         String ss = "===========Send Start==========";
         long firstTime = System.currentTimeMillis();

         for (int index = 0; index < count; index++)
         {
            bean.setSmDestAddrs(addrs);
            bean.setSmMsgContent("周雄通过A周雄通过API发送短信周雄通过API发送短信周雄通过API发送短信周雄通过API发送短信周雄通过API发送短信周雄通过API发送短信周雄通过API发送短信周雄通过API发送短信周雄通过API发送短信周雄通过API发送短信周雄通过API发送短信周雄通过API发送短信周雄通过API发送短信周雄通过API发送短信周雄通过API发送短信PI发送短信");
            bean.setSmSendTime(new Date());
            bean.setMsgFmt("15");
            bean.setSendMethodType(5);
            bean.setSmServiceId("aaaaaaaaaaaaaaaa");
            bean.setAppendOperatorID(false);
            bean.setSuboperationType("5");
            bean.setOperationType("WAS");
            bean.setSendType(9);
       //     bean.setMsgFmt("0");
            //检测。
//            if (smproxy.isConnected())
//            {
              System.out.println("................true");
              ret = smproxy.sendSm(bean);
//            }
//            else //循环等待。
//            {
//              while (!smproxy.isConnected())
//              {
//                System.out.println("................false");
//                Thread.currentThread().sleep(500);
//                try
//                {
//                  smproxy.destroy();
//                  createProxy(smproxy);
//                }
//                catch (Exception ex)
//                {
//
//                }
//              }
//            }
            System.out.println("MAS Send Result " + ret[0]);
         }

         long lastTime = System.currentTimeMillis();
         SendTime = lastTime - firstTime;
         System.out.println(" Sended:" + count + " records,consumed " + (lastTime - firstTime) + " ms");
         System.out.println("===========Send End==========");
      }

     /**
     * 测试方法主入口。
     *
     * @param args String[]
     * @throws Exception
     */
   public static void main(String[] args)
      throws Exception
   {
       //File sendFile = FileUtil.newFileDelExit("testResult.txt");
       //SendCount = Integer.parseInt(args[0]);
       //ReceiveCount = Integer.parseInt(args[1]);

       FileWriter fw = new FileWriter("testResult.txt");
       PrintWriter out = new PrintWriter(fw);
       testSendMessage();
       out.println(" Send Use Time: " + SendTime + "; Receive Use Time: " + ReceiveTime);
       out.close();
       fw.close();
       System.out.println("====all test finished.press any key to exit.======");
       System.in.read();
   }

}

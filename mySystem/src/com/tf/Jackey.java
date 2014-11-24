package com.tf;
import java.util.HashMap;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.json.JSONObject;

public class Jackey {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		Jackey jackey = new Jackey();
		//jackey.get();
		
		System.out.println( "file.syt.em".replace('.', '_') );
	}

	public void addBook(String bookName, String author) throws Exception {
		String output = null;
		try {
			String url = "http://localhost:8080/weblog4jdemo/bookservice/addbook";
			HttpClient client = new HttpClient();
			PostMethod mPost = new PostMethod(url);
			mPost.addParameter("name", "Naked Sun");
			mPost.addParameter("author", "Issac Asimov");
			Header mtHeader = new Header();
			mtHeader.setName("content-type");
			mtHeader.setValue("application/x-www-form-urlencoded");
			mtHeader.setName("accept");
			mtHeader.setValue("application/xml");
			// mtHeader.setValue("application/json");
			mPost.addRequestHeader(mtHeader);
			client.executeMethod(mPost);
			output = mPost.getResponseBodyAsString();
			mPost.releaseConnection();
			
			//mPost
			//System.out.println("output : " + output);
		} catch (Exception e) {
			throw new Exception("Exception in adding bucket : " + e);
		}
	}
	
	public void get() throws Exception {
		String output = null;
		try {
			String url = "http://16.186.78.4:13080/SM/9/rest/BusinessServices/adv-afr-desk-101";
			HttpClient client = new HttpClient();
			GetMethod mGet = new GetMethod(url);
			
			Header mtHeader = new Header();
			mtHeader.setName("Authorization");
			mtHeader.setValue("Basic ZmFsY29uOg==");
			mGet.addRequestHeader(mtHeader);
			
			client.executeMethod(mGet);
			
			/*UrlBuilder builder = new UrlBuilder("https://graph.facebook.com/oauth/access_token")
            .addParameter("client_id", application.getKey())
            .addParameter("client_secret", application.getSecret())
            .addParameter("redirect_uri", callbackURL)
            .addParameter("code", code);

			GetMethod method = new GetMethod(builder.toString());
			
			
			PostMethod mPost = new PostMethod(url);
			mPost.addParameter("name", "Naked Sun");
			mPost.addParameter("author", "Issac Asimov");
			Header mtHeader = new Header();
			mtHeader.setName("content-type");
			mtHeader.setValue("application/x-www-form-urlencoded");
			mtHeader.setName("accept");
			mtHeader.setValue("application/xml");
			// mtHeader.setValue("application/json");
			mPost.addRequestHeader(mtHeader);
			client.executeMethod(mPost);
			output = mPost.getResponseBodyAsString();*/
			output = mGet.getResponseBodyAsString();			
			mGet.releaseConnection();
			
			JSONObject json = new JSONObject(output);
			/*for(String a:json.keys()){
				
			}*/
			HashMap map = new HashMap();
			map.put("test2", "test");
			json.put("test", new JSONObject(map));
			System.out.println("output : " + json);
			System.out.println("output1 : " + json.get("ReturnCode"));
			System.out.println("output2 : " + json.get("BusinessService"));
		} catch (Exception e) {
			throw new Exception("Exception in adding bucket : " + e);
		}
	}

}

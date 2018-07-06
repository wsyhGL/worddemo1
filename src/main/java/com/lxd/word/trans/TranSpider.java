package com.lxd.word.trans;

import java.io.*;
import java.net.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * javaʵ������
 */
public class TranSpider {
	URL   targetUrl=null;
	
	public  TranSpider() throws MalformedURLException{
	    targetUrl=new URL("http://fanyi.youdao.com/translate_o?smartresult=dict&smartresult=rule&sessionFrom=");
	 
	}
	//��ȡ�������͵�message
	public List<NameValuePair>  getMessage(String word) throws Exception {
		
		//��ȡ���Ӽ��ܽṹ------ʱ����ṹ
		long timestamp=(new Date().getTime());
		int random=(int)(Math.random()*10);
		String  salt=String.valueOf(timestamp+random);
		

		
		//��ȡ���Ӽ��ܽṹ----sign
        String u = "fanyideskweb";
        String d = word;
        String f = salt;
        String c = "rY0D^0\'nM0}g5Mm1z%1G4";
        String info=(u+d+f+c);
        byte[] infobyte=info.getBytes();
        
        
        MessageDigest  messageDigest=MessageDigest.getInstance("MD5");
        messageDigest.digest(info.getBytes("utf-8"));
        // ʹ��ָ�����ֽڸ���ժҪ
        messageDigest.update(infobyte);
        // �������
        byte[] md = messageDigest.digest();
        
        // ������ת����ʮ�����Ƶ��ַ�����ʽ
        char hexDigits[] = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
        };
        int j = md.length;
        char endSign[] = new char[j * 2];
        int k = 0;
        for (int i = 0; i < j; i++) {
            byte byte0 = md[i];
            endSign[k++] = hexDigits[byte0 >>> 4 & 0xf];
            endSign[k++] = hexDigits[byte0 & 0xf];
        }
        
        //��ȡ16����  md5����  ��sign
        String sign=new String(endSign).toLowerCase();
        
        
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(); 
        

        nameValuePairs.add(new BasicNameValuePair("i",word));
        nameValuePairs.add(new BasicNameValuePair("from","AUTO"));
        nameValuePairs.add(new BasicNameValuePair("to","AUTO"));
        nameValuePairs.add(new BasicNameValuePair("smartresult","dict"));
        nameValuePairs.add(new BasicNameValuePair("client","fanyideskweb"));
        nameValuePairs.add(new BasicNameValuePair("salt",salt));
        nameValuePairs.add(new BasicNameValuePair("sign",sign));
        nameValuePairs.add(new BasicNameValuePair("doctype","json"));
        nameValuePairs.add(new BasicNameValuePair("version","2.1"));
        nameValuePairs.add(new BasicNameValuePair("keyfrom","fanyi.web"));
        nameValuePairs.add(new BasicNameValuePair("action","FY_BY_CLlCKBUTTON"));
        nameValuePairs.add(new BasicNameValuePair("typoResult","true"));
        
        
        return nameValuePairs;
        
        
        
        		
	}
	
	
	public  Header[] getHeader() {

		Header[] headers=new Header[11];
        headers[0]=new BasicHeader("Accept", "application/json, text/javascript, */*; q=0.01");
        //���ܵĸ�ʽjson   ��Ҫgzipѹ��
        //headers[1]=new BasicHeader("Accept-Encoding", "gzip, deflate");
        headers[1]=new BasicHeader("Accept-Encoding", "deflate");
        headers[2]=new BasicHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");
        headers[3]=new BasicHeader("Connection", "keep-alive");
        headers[4]=new BasicHeader("X-Requested-With", "XMLHttpRequest");
        headers[5]=new BasicHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        headers[6]=new BasicHeader("Cookie", "JSESSIONID=aaalHNVSigPD8-hsnhf3v; SESSION_FROM_COOKIE=fanyiweb; OUTFOX_SEARCH_USER_ID=526401539@113.16.65.153; _ntes_nnid=1892114ba72ae7f868a29a4db02914a0,1502250589343; _dict_cpm_show=1502250589350; _dict_cpm_close=1; OUTFOX_SEARCH_USER_ID_NCOO=1688640113.572293; ___rl__test__cookies=1502251640921");
        headers[7]=new BasicHeader("Host", "fanyi.youdao.com");
        headers[8]=new BasicHeader("Origin", "http://fanyi.youdao.com");
        headers[9]=new BasicHeader("Referer", "http://fanyi.youdao.com");
        headers[10]=new BasicHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.90 Safari/537.36");
        //headers[11]=new BasicHeader("Content-Length", "205");
        
		return headers;
	}
	
	
	
	//��ʽ����
	public TransBean  getForm(String info) {
		TransBean  transBean=new TransBean();
        
		//string-->json��
		JSONObject  jsonObject=JSONObject.fromObject(info);
    	
		//��Ҫ��ʽ��ʽת��
        JSONArray maininfo=JSONArray.fromObject(jsonObject.getString("translateResult"));
    	maininfo=((JSONArray) maininfo.get(0));
        JSONObject mainTran =JSONObject.fromObject(maininfo.get(0).toString());
    	 
    	transBean.setWord(mainTran.getString("src"));
    	transBean.addTrans(mainTran.getString("tgt"));
    	
    	
    	//��Ҫ��ʽת��
        JSONObject smarkinfo=JSONObject.fromObject(jsonObject.getString("smartResult"));
        JSONArray smarkTran=JSONArray.fromObject(smarkinfo.get("entries"));

        for(Object object:smarkTran) {
        	if(object!=""||object!=null) {
        		transBean.addTrans(object.toString().trim());
        	}
        }


        return transBean;
	}
	
	
	
	//�������̲��� :  ���뵥��-->������ص�ע��+time
	public TransBean   TranProcess(String word) throws Exception {
		
		//����-���շ�����
    	HttpClient  httpClient=new DefaultHttpClient();
    	HttpPost   httpPost  =new HttpPost();
        HttpResponse  httpResponse=null;
    
    	//���÷���message  ִ�з���
        httpPost.setURI(this.targetUrl.toURI());
    	List<NameValuePair>  nameValuePairs=this.getMessage(word);
        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"utf-8"));
        httpPost.setHeaders(this.getHeader());
       
        
        //ִ�з���
        httpResponse=httpClient.execute(httpPost);

        //��ȡʵ�巵�ؽ��
        HttpEntity entity=httpResponse.getEntity();
        String info =EntityUtils.toString(entity, "utf-8");
    
        
        //��������и�ʽת��
        TransBean  transBean=this.getForm(info);

        return  transBean;
	}
	

    
    
}
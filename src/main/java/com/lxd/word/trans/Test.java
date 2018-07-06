package com.lxd.word.trans;
	
import java.net.MalformedURLException;
import java.util.Date;
import java.util.List;
	
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

	
	public class Test {
	
		public static void main(String args[]) throws Exception {
			TranSpider  tranSpider=new TranSpider();
	        TransBean  transBean=tranSpider.TranProcess("hello");
	        System.out.println(transBean);
	        System.out.println(transBean.timestamp);    
		}
	}	

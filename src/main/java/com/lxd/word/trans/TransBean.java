 package com.lxd.word.trans;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sun.org.apache.bcel.internal.generic.NEW;

public class TransBean {

	String word=null;
    public Timestamp  timestamp=null;
	List<String> trans=null;
	
	
	public TransBean() {
		word="";
		String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());//将时间格式转换成符合Timestamp要求的格式.
		timestamp=Timestamp.valueOf(nowTime);
		trans=new ArrayList<String>();
	}


	public String getWord() {
		return word;
	}


	public void setWord(String word) {
		this.word = word;
	}


	public List<String> getTrans() {
		return trans;
	}


	public void setTrans(List<String> trans) {
		this.trans = trans;
	}

	public void addTrans(String tran) {
		this.trans.add(tran);
	}


	public Timestamp getTimestamp() {
		return timestamp;
	}


	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}


	@Override
	public String toString() {
		return "TransBean [word=" + word + ", timestamp=" + timestamp + ", trans=" + trans + "]";
	}



	
	
	
}

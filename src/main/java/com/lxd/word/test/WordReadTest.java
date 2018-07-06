package com.lxd.word.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class WordReadTest {
	@Test
	public void testRead() {
		BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(new FileReader(new File("word.txt")));
			String string;
			List<String> list = new ArrayList<>();
			while((string = bufferedReader.readLine())!=null) {
				list.add(string);
			}
			for(int i=0;i<list.size();i++) {
				string=list.get(i);
				System.out.println(string);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			if(bufferedReader!=null){
				try {
					bufferedReader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}

package com.lxd.word.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lxd.word.bean.Word;
import com.lxd.word.dao.WordMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:applicationContext.xml"})
public class MapperTest {
	@Autowired
	WordMapper wordMapper;
	@Autowired
	SqlSession sqlSession;
	
	@Test
	public void testCRUD() {
		BufferedReader bufferedReader = null;
	try {
		bufferedReader = new BufferedReader(new FileReader(new File("word.txt")));
		String string;
		String string2;
		WordMapper wordMapper = sqlSession.getMapper(WordMapper.class);
		List<String> list = new ArrayList<>();
		while((string = bufferedReader.readLine())!=null) {
			list.add(string);
		}
		for(int i=0;i<list.size()-1;i++) {
			string=list.get(i);
			i++;
			string2 = list.get(i);
			wordMapper.insertSelective(new Word(null, "GUU", 0, string, string2, new Date(), 1, "2", "2"));
			
		}
		System.out.println("批量插入成功");
		
		
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

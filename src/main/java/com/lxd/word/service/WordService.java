package com.lxd.word.service;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lxd.word.bean.Word;
import com.lxd.word.bean.WordExample;
import com.lxd.word.bean.WordExample.Criteria;
import com.lxd.word.dao.WordMapper;

@Service
public class WordService {
	@Autowired
	WordMapper wordMapper;
	/**
	 * 查询所有单词
	 */
	public List<Word> getAll(String ip){
		WordExample wordExample = new WordExample();
		Criteria criteria = wordExample.createCriteria();
		criteria.andBelongEqualTo(ip);
		return wordMapper.selectByExample(wordExample);
	}
	public void saveWord(Word word) {
		wordMapper.insertSelective(word);
	}
	public void deleteBatch(List<Integer> del_ids) {
		// TODO Auto-generated method stub
		WordExample wordExample = new WordExample();
		Criteria criteria = wordExample.createCriteria();
		
		criteria.andIdIn(del_ids);
		wordMapper.deleteByExample(wordExample);
	}
	public void deleteWord(Integer id) {
		// TODO Auto-generated method stub
		wordMapper.deleteByPrimaryKey(id);
	}

}

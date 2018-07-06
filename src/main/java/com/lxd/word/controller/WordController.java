package com.lxd.word.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lxd.word.bean.Msg;
import com.lxd.word.bean.Word;
import com.lxd.word.service.WordService;
import com.lxd.word.trans.TranSpider;
import com.lxd.word.trans.TransBean;

/**
 * 处理单词的增删改查
 * @author lilua
 *
 */
@Controller
public class WordController {
	@Autowired
	WordService wordService;
	
	@ResponseBody
	@RequestMapping(value="/emp/{ids}",method = RequestMethod.DELETE)
	public Msg deleteWord(@PathVariable("ids") String ids) {
		if(ids.contains("-")) {
			List<Integer> del_ids = new ArrayList<>();
			String [] str_ids= ids.split("-");
			for(String string :str_ids) {
				del_ids.add(Integer.parseInt(string));
			}
			wordService.deleteBatch(del_ids);
		}else {
			Integer id = Integer.parseInt(ids);
			wordService.deleteWord(id);
		}
		return Msg.success();
	}
	
	/**
	 * 保存单词
	 */
	@RequestMapping(value="/emp",method=RequestMethod.POST)
	@ResponseBody
	public Msg saveWord(Word word,HttpServletRequest  request) {
		String clientIp=request.getRemoteAddr().toString();
		word.setInserttime(new Date());
		word.setBelong(clientIp);
		wordService.saveWord(word);
		System.out.println(word.getBelong());
		return Msg.success();
	}
	/**
	 * 保存插件传来的单词
	 */
	@RequestMapping(value="/word",method=RequestMethod.POST)
	@ResponseBody
	public Msg saveNetWord(Word word,HttpServletRequest  request) throws Exception{
		String clientIp=request.getRemoteAddr().toString();
		TranSpider  tranSpider  =new TranSpider();
		TransBean  transBean  =tranSpider.TranProcess(word.getOrigin());
		StringBuffer stringBuffer = new StringBuffer();
		for(String str:transBean.getTrans()) {
			stringBuffer.append(str);
		}
		word.setBelong(clientIp);
		word.setEnd(stringBuffer.toString());
		word.setInserttime(new Date());
		wordService.saveWord(word);
		System.out.println("新插入了"+word.getOrigin());
		return Msg.success();
	}
	
	/**
	 * 查询所有单词
	 */
	@RequestMapping("/emps")
	@ResponseBody
	public Msg getWordWithJson(@RequestParam(value="pn", defaultValue="1")Integer pn,HttpServletRequest  request) {
		PageHelper.startPage(pn, 5);
		String clientIp=request.getRemoteAddr().toString();
		List<Word> words = wordService.getAll(clientIp);
		PageInfo page = new PageInfo(words,5);
		return Msg.success().add("pageInfo", page);
	}

}

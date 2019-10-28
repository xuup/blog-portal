package com.blog.controller.base;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.blog.dto.ArticleDto;
import com.blog.dto.LabelDto;
import com.blog.dto.UrlLinksDto;
import com.blog.dto.WxToolDto;
import com.blog.service.ArticleService;
import com.google.gson.Gson;

@Controller
@RequestMapping("/")
public class BaseController {
	
	Logger logger = LoggerFactory.getLogger(BaseController.class);
	
	@Autowired
	private ArticleService articleService;
	
	private Gson gosn;
	
	@RequestMapping("/")
	public String index(String keyWord, Integer categoryId, ModelMap map){
		//获取文章总数
		int total = articleService.getTotal(keyWord, categoryId);
		map.put("total", total);
		map.put("curentPage", 0);
		map.put("keyWord", keyWord);
		map.put("categoryId", categoryId);
		return "index";
	}
	
	@ResponseBody
	@RequestMapping("getArticle")
	public List<ArticleDto> getArticle(ArticleDto articleConditionDto){
		gosn = new Gson();
		logger.info("offset: {},limit: {},keyWord:{}",articleConditionDto.getOffset(), articleConditionDto.getLimit(), articleConditionDto.getKeyWord());
		List<ArticleDto> res = articleService.getArticle(articleConditionDto);
		logger.info("res.........{}", gosn.toJson(res));
		return res;
	}
	
	
	@ResponseBody
	@RequestMapping("getLabels")
	public List<LabelDto> getLabels(){
		List<LabelDto> res = articleService.getLabels();
		return res;
	}
	
	
	@ResponseBody
	@RequestMapping("getHotArticle")
	public List<ArticleDto> getHotArticle(){
		List<ArticleDto> res = articleService.getHotArticle();
		return res;
	}
	
	
	@RequestMapping("detail")
	public String detail(int articleId, ModelMap map){
		logger.info("articleId:  {}",articleId);
		//获取文章总数
		ArticleDto articleDto = articleService.selectArticleById(Integer.valueOf(articleId));
		map.put("article", articleDto);
		return "content/detail";
	}
	
	
	@ResponseBody
	@RequestMapping("getUrlLinks")
	public List<UrlLinksDto> getUrlLinks() {
		return articleService.getUrlLinks();
	}
	
	@RequestMapping("aboutMe")
	public String aboutMe() {
		return "content/aboutMe";
	}
	
	
	@RequestMapping("timeLine")
	public String timeLine(String keyWord, Integer categoryId, ModelMap map) {
		//获取文章总数
		int total = articleService.getTotal(keyWord, categoryId);
		map.put("total", total);
		map.put("curentPage", 0);
		map.put("keyWord", keyWord);
		map.put("categoryId", categoryId);
		return "content/timeLine";
	}
	
	
	@RequestMapping("wxTool")
	public String wxTool(String keyWord, Integer categoryId, ModelMap map) {
		//获取文章总数
		int total = articleService.getTotal(keyWord, categoryId);
		map.put("total", total);
		map.put("curentPage", 0);
		map.put("keyWord", keyWord);
		map.put("categoryId", categoryId);
		return "content/tool";
	}
	
	
	
	@RequestMapping("getWxTools")
	@ResponseBody
	public List<WxToolDto> getWxTools(){
		List<WxToolDto> res = new ArrayList<WxToolDto>();
		res = articleService.getWxTools();
		logger.info("get wxtools: {}", res);
		return res;
	}
	
}

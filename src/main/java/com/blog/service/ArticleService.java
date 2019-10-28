package com.blog.service;

import java.util.List;

import com.blog.dto.ArticleDto;
import com.blog.dto.LabelDto;
import com.blog.dto.UrlLinksDto;
import com.blog.dto.WxToolDto;

public interface ArticleService {
	
	public List<ArticleDto> getArticle(ArticleDto articleConditionDto);
	
	public int getTotal(String keyWord, Integer categoryId);
	
	public List<LabelDto> getLabels();
	
	public List<ArticleDto> getHotArticle();
	
	public ArticleDto selectArticleById(int id);
	
	public List<UrlLinksDto> getUrlLinks();
	
	public List<WxToolDto> getWxTools();
}

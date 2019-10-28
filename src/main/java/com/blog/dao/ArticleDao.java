package com.blog.dao;

import java.util.List;
import java.util.Map;


import com.blog.dto.ArticleDto;
import com.blog.dto.UrlLinksDto;


public interface ArticleDao {
	
	
	public List<ArticleDto> selectArticle(ArticleDto articleDto);
	
	public int countArticle(Map<String,Object> map);
	
	public void saveArticle(ArticleDto articleDto);
	
	public void updateArticle(ArticleDto articleDto);
	
	public ArticleDto selectArticleById(int id);
	
	public List<ArticleDto> selectHotArticle();
	
	public List<UrlLinksDto> getUrlLinks();

}

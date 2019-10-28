package com.blog.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.blog.dao.ArticleDao;
import com.blog.dao.LabelDao;
import com.blog.dao.WxToolDao;
import com.blog.dto.ArticleDto;
import com.blog.dto.LabelDto;
import com.blog.dto.UrlLinksDto;
import com.blog.dto.WxToolDto;
import com.blog.util.JedisPoolUtil;
import com.blog.util.SerializeUtil;

@Service
public class ArticleServiceImpl implements ArticleService{
	
	@Autowired
	private ArticleDao articleDao;
	
	@Autowired
	private LabelDao labelDao;
	
	@Autowired
	private WxToolDao wxToolDao;
	
	public List<ArticleDto> getArticle(ArticleDto articleConditionDto) {
		
		List<ArticleDto> res = articleDao.selectArticle(articleConditionDto);
		
		return res;
	}
	
	
	
	public int getTotal(String keyWord, Integer categoryId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("keyWord", keyWord);
		map.put("category", categoryId);
		int total = articleDao.countArticle(map);
		return total;
	}
	
	
	public List<LabelDto> getLabels(){
		List<LabelDto> list = labelDao.getLabels();
		return list;
	}
	
	
	public List<ArticleDto> getHotArticle(){
		List<ArticleDto> list = articleDao.selectHotArticle();
		return list;
	}
	
	
	@Transactional
	public ArticleDto selectArticleById(int id){
		ArticleDto articleDto = null;
		String jedisId = String.valueOf(id);
		//鍒ゆ柇redis涓槸鍚﹀瓨鍦�
		JedisPool jedisPool = JedisPoolUtil.getJedisPoolInstance();
		Jedis jedis = jedisPool.getResource();
		if(jedis.exists(jedisId)){
			articleDto = (ArticleDto) SerializeUtil.unSerialize(jedis.get(jedisId.getBytes()));
			jedis.expire(jedisId.getBytes(), 5*60*60);
		}else{
			articleDto = articleDao.selectArticleById(id);
			articleDto.setReadNumber(articleDto.getReadNumber()+1);
			articleDao.updateArticle(articleDto);
			jedis.set(jedisId.getBytes(), SerializeUtil.serialize(articleDto));
			jedis.expire(jedisId, 5*60*60);
		}
		
		return articleDto;
	}



	public List<UrlLinksDto> getUrlLinks() {
		return articleDao.getUrlLinks();
	}


	public List<WxToolDto> getWxTools() {
		List<WxToolDto> list = new ArrayList<WxToolDto>();
		list = wxToolDao.getWxTools();
		return list;
	}

}

package com.blog.dao;

import java.util.List;

import com.blog.dto.LabelDto;

public interface LabelDao {
	
	public List<LabelDto> getLabels();
}

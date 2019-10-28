package com.blog.base;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

public class BaseController {
	
	@RequestMapping("init")
	public String index(){
		return "index";
	}
	
}

package com.utstar.networkshop.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestSpringmvc {
	@RequestMapping("/hellotest")
	public String test(){
		return "hello";
	}
}

package com.utstar.networkshop.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CenterControler {
	@RequestMapping(value="/control/index")
	public String index(){
		return "index";
	}
	@RequestMapping(value="/control/top")
	public String top(){
		return "top";
	}
	@RequestMapping(value="/control/main")
	public String main(){
		return "main";
	}
	@RequestMapping(value="/control/left")
	public String left(){
		return "left";
	}
	@RequestMapping(value="/control/right")
	public String right(){
		return "right";
	}
}

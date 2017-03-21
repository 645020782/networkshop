package com.utstar.networkshop.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

















import com.octo.captcha.service.image.ImageCaptchaService;
import com.utstar.common.encode.Md5Pwd;
import com.utstar.common.utils.ResponseUtils;
import com.utstar.common.web.contants.Constants;
import com.utstar.common.web.session.SessionProvider;
import com.utstar.networkshop.domain.Buyer;
import com.utstar.networkshop.domain.City;
import com.utstar.networkshop.domain.Province;
import com.utstar.networkshop.domain.Town;
import com.utstar.networkshop.service.BuyerService;
import com.utstar.networkshop.service.CityService;
import com.utstar.networkshop.service.ProvinceService;
import com.utstar.networkshop.service.TownService;

/**
 * 跳转登陆页面
 * 登陆
 * 个人资料
 * 收货地址
 * @author lx
 *
 */
@Controller
@RequestMapping(value="/control")
public class ProfileController {
	
	@Autowired
	private SessionProvider sessionProvider;
	@Autowired
	private Md5Pwd md5Pwd;
	@Autowired
	private BuyerService buyerService;
	@Autowired
	private ImageCaptchaService imageCaptchaService;
	@Autowired
	private ProvinceService provinceService;
	@Autowired
	private CityService cityService;
	@Autowired
	private TownService townService;
	//GET
	@RequestMapping(value = "/shopping/login.shtml",method=RequestMethod.GET)
	public String login(){
		return "buyer/login";
	}
	//POST
	/**
	 * 1:验证码是否为null
		2:验证码  是否正确
		3:用户是否为NUll
		4:密码是否为NUll
		5:用户是否正确
		6密码是否正确
		Md5  纯生Md5 
		放进Session   跳转ReturnUrl
	 * @param buyer
	 * @param captcha
	 * @param returnUrl
	 * @return
	 */
	@RequestMapping(value = "/shopping/login.shtml",method=RequestMethod.POST)
	public String login(Buyer buyer,String captcha,String returnUrl,ModelMap model,HttpServletRequest request, HttpServletResponse response){
		//验证码是否为null
		if(StringUtils.isNotBlank(captcha)){
			//1:JSESSIONID
			//2验证码
			if(imageCaptchaService.validateResponseForID(sessionProvider.getSessionId(request, response), captcha)){
				if(null != buyer && StringUtils.isNotBlank(buyer.getUsername())){
					if(StringUtils.isNotBlank(buyer.getPassword())){
						Buyer b = buyerService.getBuyerByKey(buyer.getUsername());
						if(null != b){
							//
							System.out.println(md5Pwd.encode(buyer.getPassword()));
							if(b.getPassword().equals(md5Pwd.encode(buyer.getPassword()))){
								//把用户对象放在Session
								sessionProvider.setAttribute(request, response, Constants.BUYER_SESSION, b);
								Object bname = request.getSession().getAttribute(Constants.BUYER_SESSION);
								if(StringUtils.isNotBlank(returnUrl)){
									return "redirect:" + returnUrl;
								}else{
									//个人中心
									return "redirect:/control/buyer/index.shtml" ;
								}
							}else{
								model.addAttribute("error", "密码错误");
							}
						}else{
							model.addAttribute("error", "用户名输入错误");
						}
					}else{
						model.addAttribute("error", "请输入密码");
					}
				}else{
					model.addAttribute("error", "请输入用户名");
				}
			}else{
				model.addAttribute("error", "验证码输入错误");
			}
		}else{
			model.addAttribute("error", "请填写验证码");
		}
		return "buyer/login";
	}
	//个人中心
	@RequestMapping(value = "/buyer/index.shtml")
	public String index(){
		return "buyer/index";
	}
	//个人资料
	@RequestMapping(value = "/buyer/profile.shtml")
	public String profile(HttpServletRequest request,ModelMap model, HttpServletResponse response){
		//加载用户
		Buyer buyer = (Buyer) sessionProvider.getAttribute(request, response, Constants.BUYER_SESSION);
		Buyer b = buyerService.getBuyerByKey(buyer.getUsername());
		model.addAttribute("buyer", b);
		//省
		List<Province> provinces = provinceService.getProvinceList(null);
		model.addAttribute("provinces", provinces);
		//市 
		City city = new City();
		List<City> citys = cityService.getCityList(city);
		model.addAttribute("citys", citys);
		//县
		Town  townQuery = new Town();
		List<Town> towns = townService.getTownList(townQuery);
		model.addAttribute("towns", towns);
		
		return "buyer/profile";
	}
	//
	@RequestMapping(value = "/buyer/city.shtml")
	public void city(String code,HttpServletResponse response){
		City city = new City();
		List<City> citys = cityService.getCityList(city);
		
		JSONObject jo = new JSONObject();
		jo.put("citys", citys);
		ResponseUtils.renderJson(response, jo.toString());
		
	}
	//收货地址
	@RequestMapping(value = "/buyer/deliver_address.shtml")
	public String address(){
		
		return "buyer/deliver_address";
	}
	
}

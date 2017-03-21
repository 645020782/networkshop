package com.utstar.networkshop.controller.admin;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.utstar.networkshop.domain.Brand;
import com.utstar.networkshop.domain.Pagination;
import com.utstar.networkshop.service.BrandService;

@Controller
@RequestMapping(value="/control")
public class BrandController {
	@Autowired
	private BrandService brandService;
	@RequestMapping(value="/brand/list")
	public String list(String brandName,String isDisplay,Integer beginPage,ModelMap model){
		System.out.println("brandService========================"+brandService);
		Brand brand = new Brand();
		if(beginPage!=null){
			brand.setCurrentPage(beginPage);
		}
		if(brandName != null && StringUtils.isNotBlank(brandName)){
			brand.setBrandName(brandName);
		}
		if(isDisplay!=null){
			brand.setIsDisplay(isDisplay);
		}
		@SuppressWarnings("unchecked")
		Pagination<Brand> pagination = brandService.getBrandListWithPage(brand);
		model.addAttribute("pagination", pagination);
		model.addAttribute("brandName",brandName);
		model.addAttribute("isDisplay", isDisplay);
		return "brand/list";
	}
	@RequestMapping(value="/brand/toEdit")
	public String toEdit(Integer brandId,String brandName,ModelMap model){
		Brand brand = new Brand();
		if(brandName != null && StringUtils.isNotBlank(brandName)){
			brand.setBrandName(brandName);
		}
		if(brandId != null){
			brand.setBrandId(brandId);
		}
		brand = brandService.getBrandById(brandId);
		model.addAttribute("brand",brand);
		//model.addAttribute("brandId", brandId);
		return "brand/edit";
	}
	@RequestMapping(value="/brand/edit")
	public String edit(Brand brand,@RequestParam(value="brandId")Integer brandId,@RequestParam(value="brandName") String brandName,ModelMap model){
		brandService.editBrandById(brand);
		model.addAttribute("brandName", brandName);
		return "redirect:../brand/list.do";
	}
	@RequestMapping(value="/brand/delete")
	public String delete(@RequestParam(value="brandId") Integer brandId,@RequestParam(value="brandName") String brandName,ModelMap model){
		Brand brand = new Brand();
		if(brandName != null && StringUtils.isNotBlank(brandName)){
			brand.setBrandName(brandName);
		}
		if(brandId != null){
			brand.setBrandId(brandId);
		}
		brandService.delBrandById(brand);
		model.addAttribute("brandName",brandName);
		//model.addAttribute("brandId", brandId);
		return "redirect:../brand/list.do";
	}
	@RequestMapping(value="/brand/toAdd")
	public String toAdd(){
		return "brand/add";
	}
	@RequestMapping(value="/brand/add")
	public String add(Brand brand, @RequestParam(value="brandName")String brandName,ModelMap model){
		brandService.addBrand(brand);
		model.addAttribute("brandName", brandName);
		return "redirect:../brand/list.do";
	}
}

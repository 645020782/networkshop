package com.utstar.networkshop.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.utstar.networkshop.domain.Brand;
import com.utstar.networkshop.domain.Color;
import com.utstar.networkshop.domain.Feature;
import com.utstar.networkshop.domain.Pagination;
import com.utstar.networkshop.domain.Product;
import com.utstar.networkshop.domain.Sku;
import com.utstar.networkshop.domain.Type;
import com.utstar.networkshop.service.BrandService;
import com.utstar.networkshop.service.FeatureService;
import com.utstar.networkshop.service.ProductService;
import com.utstar.networkshop.service.SkuService;
import com.utstar.networkshop.service.TypeService;

@Controller
@RequestMapping(value = "/control")
public class FrontProductController {
	@Autowired
	private BrandService brandService;
	@Autowired
	private ProductService productService;
	@Autowired
	private TypeService typeService;
	@Autowired
	private FeatureService featureService;
	@Autowired
	private SkuService skuService;
	
	//商品列表页面
	@RequestMapping(value = "/product/display/list.shtml")
	public String list(Integer pageNo,Integer brandId,String brandName,Integer typeId,String typeName,ModelMap model){
		//加载商品属性
		Feature feature = new Feature();
		List<Feature> features = featureService.getFeatureList(feature);
		//显示在页面
		model.addAttribute("features", features);
		//设置页号
		Product product = new Product();
		//品牌ID
		if(null != brandId){
			product.setBrandId(brandId);
			//显示在页面
			model.addAttribute("brandId", brandId);
			model.addAttribute("brandName", brandName);
		}else{
			//加载商品品牌
			//品牌条件对象
			Brand brand = new Brand();
			//设置指定字段
			//设置可见
			brand.setIsDisplay("1");
			//加载品牌
			List<Brand> brands = brandService.getAllBrand(brand);
			//显示在页面
			model.addAttribute("brands", brands);
		}
		//类型ID
		if(null != typeId){
			product.setTypeId(String.valueOf(typeId));
			//显示在页面
			model.addAttribute("typeId", typeId);
			model.addAttribute("typeName", typeName);
		}else{
			//加载商品类型
			Type type = new Type();
			//指定查询哪些字段
			type.setIsDisplay(1);
			type.setParentId(0);
			List<Type> types = typeService.getTypeList(type);
			//显示在页面
			model.addAttribute("types", types);
		}
		//加载带有分页的商品
		Pagination pagination = productService.getProductListWithPage(product);
		//分页页面展示    //String params = "brandId=1&name=2014瑜伽服套装新款&pageNo=1";
		model.addAttribute("pagination", pagination);
		return "product/product";
	}
	
	//跳转商品详情页
		@RequestMapping(value = "/product/detail.shtml")
		public String detail(Integer productId,ModelMap model){
			//商品加载
			Product product = productService.getProductById(productId);
			
			model.addAttribute("product", product);
			
			//skus
			List<Sku> skus = skuService.getStock(productId);
			model.addAttribute("skus", skus);
			//去重复
			List<Color>  colors = new ArrayList<Color>();
			//遍历SKu
			for(Sku sku : skus){
				//判断集合中是否已经有此颜色对象了
				if(!colors.contains(sku.getColor())){
					colors.add(sku.getColor());
				}
			}
			model.addAttribute("colors", colors);
			
			return "product/productDetail";
		}
	
	//每一个Springmvc
	@RequestMapping(value = "/test/springmvc.do")
	public String test(String name,Date birthday){
		
		
		System.out.println();
		return "";
	}

/*	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request) {
		//转换日期格式
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
		
	}
	*/
}

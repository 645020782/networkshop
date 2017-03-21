package com.utstar.networkshop.controller.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.utstar.networkshop.domain.Brand;
import com.utstar.networkshop.domain.Color;
import com.utstar.networkshop.domain.Feature;
import com.utstar.networkshop.domain.Image;
import com.utstar.networkshop.domain.Pagination;
import com.utstar.networkshop.domain.Product;
import com.utstar.networkshop.domain.Sku;
import com.utstar.networkshop.domain.Type;
import com.utstar.networkshop.service.BrandService;
import com.utstar.networkshop.service.ColorService;
import com.utstar.networkshop.service.FeatureService;
import com.utstar.networkshop.service.ImageService;
import com.utstar.networkshop.service.ProductService;
import com.utstar.networkshop.service.SkuService;
import com.utstar.networkshop.service.TypeService;
import com.utstar.networkshop.service.staticpage.StaticPageService;

@Controller
@RequestMapping(value="/control")
public class ProductController {
	@Autowired
	private ProductService productService;
	@Autowired
	private TypeService typeService;
	@Autowired
	private FeatureService featureService;
	@Autowired
	private ImageService imageService;
	@Autowired
	private BrandService brandService;
	@Autowired
	private ColorService colorService;
	@Autowired
	private SkuService skuService;
	@Autowired
	private StaticPageService staticPageService;
	@RequestMapping(value="/product/list")
	public String list(String productName,String isDisplay,Integer beginPage,ModelMap model){
		Product product = new Product();
		if(beginPage!=null){
			product.setCurrentPage(beginPage);
		}
		if(productName != null && StringUtils.isNotBlank(productName)){
			product.setName(productName);
		}
		if(isDisplay!=null){
			product.setIsShow(isDisplay);
		}
		@SuppressWarnings("unchecked")
		Pagination<Product> pagination = productService.getProductListWithPage(product);
		model.addAttribute("pagination", pagination);
		model.addAttribute("ProductName",productName);
		model.addAttribute("isDisplay", isDisplay);
		return "product/list";
	}
	@RequestMapping(value="/product/toEdit")
	public String toEdit(Integer productId,String productName,ModelMap model){
		Product product = new Product();
		product = productService.getProductById(productId);
		Image img = new Image();
		Image image = imageService.getImageByProductId(img).get(0);
		Type type = new Type();
		type.setIsDisplay(1);
		List<Type> allType = typeService.getAllType(type);
		Feature feature = new Feature();
		feature.setIsdel(0);
		List<Feature> allFeature = featureService.getAllFeature(feature);
		Brand brand = new Brand();
		brand.setIsDisplay("1");
		List<Brand> allBrand = brandService.getAllBrand(brand);
		Color color = new Color();
		List<Color> allColor = colorService.getAllColor(color);
		model.addAttribute("allFeature",allFeature);
		model.addAttribute("allType",allType);
		model.addAttribute("image",image);
		model.addAttribute("product",product);
		model.addAttribute("allBrand",allBrand);
		model.addAttribute("allColor",allColor);
		return "product/edit";
	}
	@RequestMapping(value="/Product/edit")
	public String edit(Product product,@RequestParam(value="productId")Integer productId,@RequestParam(value="productName") String productName,ModelMap model){
		productService.editProductById(product);
		model.addAttribute("productName", productName);
		return "redirect:../product/list.do";
	}
	@RequestMapping(value="/product/delete")
	public String delete(@RequestParam(value="productId") Integer productId,@RequestParam(value="productName") String productName,ModelMap model){
		Product product = new Product();
		if(productName != null && StringUtils.isNotBlank(productName)){
			product.setName(productName);
		}
		if(productId != null){
			product.setProductId(productId);
		}
		productService.delProductById(product);
		model.addAttribute("productName",productName);
		//model.addAttribute("ProductId", ProductId);
		return "redirect:../product/list.do";
	}
	@RequestMapping(value="/product/toAdd")
	public String toAdd(ModelMap model){
		Type type = new Type();
		type.setIsDisplay(1);
		List<Type> allType = typeService.getAllType(type);
		Feature feature = new Feature();
		feature.setIsdel(0);
		List<Feature> allFeature = featureService.getAllFeature(feature);
		Brand brand = new Brand();
		brand.setIsDisplay("1");
		List<Brand> allBrand = brandService.getAllBrand(brand);
		Color color = new Color();
		List<Color> allColor = colorService.getAllColor(color);
		model.addAttribute("allFeature",allFeature);
		model.addAttribute("allType",allType);
		model.addAttribute("allBrand",allBrand);
		model.addAttribute("allColor",allColor);
		return "product/add";
	}
	@RequestMapping(value="/product/add")
	public String add(Product product,Image image){
		product.setImg(image);
		productService.addProduct(product);
		//model.addAttribute("productName", productName);
		return "redirect:../product/list.do";
	}
	
	@RequestMapping(value = "/product/isShow.do")
	public String isShow(Integer[] ids,Integer currentPage,String name,Integer brandId,Integer isShow,ModelMap model){
		//实例化商品
		//上架
		if(null != ids && ids.length >0){
			for(Integer id : ids){
				Product p = productService.getProductById(id);
				p.setIsShow("1");
				//修改上架状态
				productService.editProductById(p);
				//TODO  静态化 
				Map<String,Object> root = new HashMap<String,Object>();
				//设置值
				//商品加载
				
				root.put("product", p);
				
				//skus
				List<Sku> skus = skuService.getStock(id);
				root.put("skus", skus);
				//去重复
				List<Color>  colors = new ArrayList<Color>();
				//遍历SKu
				for(Sku sku : skus){
					//判断集合中是否已经有此颜色对象了
					if(!colors.contains(sku.getColor())){
						colors.add(sku.getColor());
					}
				}
				root.put("colors", colors);
				staticPageService.productIndex(root, id);
			}
		}
		
		
		
		//判断
		if(StringUtils.isNotBlank(name)){
			model.addAttribute("name", name);
		}
		if(null != brandId){
			model.addAttribute("brandId", brandId);
		}
		if(null != isShow){
			model.addAttribute("isShow", isShow);
		}
		
		return "redirect:../product/list.do";
	}
}

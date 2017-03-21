package com.utstar.networkshop.controller.admin;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.utstar.common.utils.ResponseUtils;
import com.utstar.networkshop.domain.Sku;
import com.utstar.networkshop.service.SkuService;


/**
 * 库存管理
 * 修改库存
 * @author lx
 *
 */
@Controller
@RequestMapping(value = "/control")
public class SkuController {

	@Autowired
	private SkuService skuService;
	//跳转到库存管理页面
	@RequestMapping(value = "/sku/list")
	public String list(Integer productId,String pno ,ModelMap model){
		
		//商品编号回显
		model.addAttribute("pno", pno);
		
		//最小销售单元  通过商品ID
		Sku sku = new Sku();
		sku.setProductId(productId);
		List<Sku> skus = skuService.getSkuList(sku);
		
		//商品编号回显
		model.addAttribute("skus", skus);
		
		return "sku/list";
	}
	//保存/修改
	@RequestMapping(value = "/sku/add.do")
	public void add(Sku sku ,ModelMap model,HttpServletResponse response){
		//修改
		skuService.updateSkuByKey(sku);
		//
		JSONObject jo = new JSONObject();
		jo.put("message", "保存成功!");
		
		ResponseUtils.renderJson(response, jo.toString());
		
	}
}

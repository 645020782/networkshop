package com.utstar.networkshop.controller;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.utstar.common.web.contants.Constants;
import com.utstar.common.web.session.SessionProvider;
import com.utstar.networkshop.domain.Addr;
import com.utstar.networkshop.domain.BuyCart;
import com.utstar.networkshop.domain.BuyItem;
import com.utstar.networkshop.domain.Buyer;
import com.utstar.networkshop.domain.Sku;
import com.utstar.networkshop.service.AddrService;
import com.utstar.networkshop.service.SkuService;


/**
 * 购物车
 * @author lx
 *
 */
@Controller
@RequestMapping(value="/control")
public class CartController {
	@Autowired
	private AddrService addrService;
	@Autowired
	private SessionProvider sessionProvider;
	//购买按钮
	@RequestMapping(value = "/shopping/buyCart.shtml")
	public String buyCart(Integer skuId,Integer amount,Integer buyLimit,Integer productId,HttpServletRequest request ,HttpServletResponse response,ModelMap model){
		//第一步:Sku
			//springmvc 
			ObjectMapper  om = new ObjectMapper();
			om.setSerializationInclusion(Inclusion.NON_NULL);
			
			//声明
			BuyCart buyCart = null;
			//判断Cookie是否有购物车  
			
			//JESSIONID
			//buyCart_cookie
			//  
			Cookie[] cookies = request.getCookies();
			if(null != cookies && cookies.length >0){
				for(Cookie c : cookies){
					if(Constants.BUYCART_COOKIE.equals(c.getName())){
						//如果有了  就使用此购物车
						String value = c.getValue();//
						//
						try {
							buyCart = om.readValue(value, BuyCart.class);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						break;
					}
				}
			}
			//如果有了  就使用此购物车
			//如果没有 创建购物车
			
			if(null == buyCart){
				//购物车   //最后一款
				buyCart = new BuyCart();
			}
			//
		if(null != skuId){
			Sku sku = new Sku();
			sku.setSkuId(skuId);
			//限制
			if(null != buyLimit){
				sku.setSkuUpperLimit(buyLimit);
			}
			//创建购物项
			BuyItem buyItem = new BuyItem();
			
			buyItem.setSku(sku);
			//数量  1 2 3  -1
			buyItem.setAmount(amount);
			//添加购物项
			buyCart.addItem(buyItem);
			//最后一款商品的ID
			if(null != productId){
				buyCart.setProductId(productId);
			}
			
			//流
			StringWriter str = new StringWriter(); 
			//对象转Json  写的过程     Json是字符串流
			try {
				om.writeValue(str, buyCart);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//购物车装进Cookie中   对象转Json
			Cookie cookie = new Cookie(Constants.BUYCART_COOKIE,str.toString());
			//关闭浏览器 也要有Cookie
			//默认是 -1  关闭浏览器 就没了
			//消灭  0  马上就没有
			//expiry 秒
			cookie.setMaxAge(60*60*24);
			//路径
			///shopping/buyCart.shtml
			//默认  /shopping
			//  /shopping
			// /buyer/*.shtml
			cookie.setPath("/");
			//发送
			response.addCookie(cookie);
		}
		//装购物车装满
		List<BuyItem> items = buyCart.getItems();
		for(BuyItem item : items){
			Sku s = skuService.getSkuByKey(item.getSku().getSkuId());
			item.setSku(s);
			//小计
		}
		//小计
		
		model.addAttribute("buyCart", buyCart);
		
		return "product/cart";
	}
	//清空购物车
	@RequestMapping(value = "/shopping/clearCart.shtml")
	public String clearCart(HttpServletRequest request,HttpServletResponse response){

		Cookie cookie = new Cookie(Constants.BUYCART_COOKIE,null);
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);

		
		return "redirect:/shopping/buyCart.shtml";
	}
	//删除一个购物项
	@RequestMapping(value = "/shopping/deleteItem.shtml")
	public String deleteItem(HttpServletRequest request,Integer skuId,HttpServletResponse response){
		//springmvc 
		ObjectMapper  om = new ObjectMapper();
		om.setSerializationInclusion(Inclusion.NON_NULL);
		
		//声明
		BuyCart buyCart = null;
		//判断Cookie是否有购物车  
		
		//JESSIONID
		//buyCart_cookie
		//  
		Cookie[] cookies = request.getCookies();
		if(null != cookies && cookies.length >0){
			for(Cookie c : cookies){
				if(Constants.BUYCART_COOKIE.equals(c.getName())){
					//如果有了  就使用此购物车
					String value = c.getValue();//
					//
					try {
						buyCart = om.readValue(value, BuyCart.class);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				}
			}
		}
		if(null != buyCart){
			Sku sku = new Sku();
			sku.setSkuId(skuId);
			//创建购物项
			BuyItem buyItem = new BuyItem();
			buyItem.setSku(sku);
			
			buyCart.deleteItem(buyItem);
			
			//流
			StringWriter str = new StringWriter(); 
			//对象转Json  写的过程     Json是字符串流
			try {
				om.writeValue(str, buyCart);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//购物车装进Cookie中   对象转Json
			Cookie cookie = new Cookie(Constants.BUYCART_COOKIE,str.toString());
			//关闭浏览器 也要有Cookie
			//默认是 -1  关闭浏览器 就没了
			//消灭  0  马上就没有
			//expiry 秒
			cookie.setMaxAge(60*60*24);
			//路径
			///shopping/buyCart.shtml
			//默认  /shopping
			//  /shopping
			// /buyer/*.shtml
			cookie.setPath("/");
			//发送
			response.addCookie(cookie);

		}
		return "redirect:/control/shopping/buyCart.shtml";
	}
	//结算
		@RequestMapping(value = "/buyer/trueBuy.shtml")
		public String trueBuy(HttpServletRequest request,HttpServletResponse response,ModelMap model){
			//springmvc 
			ObjectMapper  om = new ObjectMapper();
			om.setSerializationInclusion(Inclusion.NON_NULL);
			
			//声明
			BuyCart buyCart = null;
			//判断Cookie是否有购物车  
			
			//JESSIONID
			//buyCart_cookie
			//  
			Cookie[] cookies = request.getCookies();
			if(null != cookies && cookies.length >0){
				for(Cookie c : cookies){
					if(Constants.BUYCART_COOKIE.equals(c.getName())){
						//如果有了  就使用此购物车
						String value = c.getValue();//
						//
						try {
							buyCart = om.readValue(value, BuyCart.class);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						break;
					}
				}
			}
			//判断购物车中是否有商品
			if(null != buyCart){
				//判断购物车中的商品是否还有库存
				List<BuyItem> items = buyCart.getItems();
				if(null != items && items.size()> 0){
					//购物车的商品项
					Integer i = items.size();
					
					//判断购物车中的商品是否还有库存
					for(BuyItem it : items){
						//
						Sku sku = skuService.getSkuByKey(it.getSku().getSkuId());
						//判断库存
						if(sku.getStockInventory() < it.getAmount()){
							//删除此商品
							buyCart.deleteItem(it);
						}
					}
					//清理后商品项个数   l=0
					Integer l =items.size();
					//判断清理前后
					if(i > l){
						//修改Cookie中的购物车数据
						//流
						StringWriter str = new StringWriter(); 
						//对象转Json  写的过程     Json是字符串流
						try {
							om.writeValue(str, buyCart);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						//购物车装进Cookie中   对象转Json
						Cookie cookie = new Cookie(Constants.BUYCART_COOKIE,str.toString());
						//关闭浏览器 也要有Cookie
						//默认是 -1  关闭浏览器 就没了
						//消灭  0  马上就没有
						//expiry 秒
						cookie.setMaxAge(60*60*24);
						//路径
						///shopping/buyCart.shtml
						//默认  /shopping
						//  /shopping
						// /buyer/*.shtml
						cookie.setPath("/");
						//发送
						response.addCookie(cookie);
						
						return "redirect:/control/shopping/buyCart.shtml";
					}else{
						//收货地址加载
						Buyer buyer = (Buyer) sessionProvider.getAttribute(request,response,Constants.BUYER_SESSION);
						Addr addr = new Addr();
						addr.setBuyerId(buyer.getId());
						//默认是1
						addr.setIsDef(1);
						
						List<Addr> addrs = addrService.getAddrList(addr);
						
						model.addAttribute("addr", addrs.get(0));
						//装购物车装满
						List<BuyItem> its = buyCart.getItems();
						for(BuyItem item : its){
							Sku s = skuService.getSkuByKey(item.getSku().getSkuId());
							item.setSku(s);
							//小计
						}
						//小计
						
						model.addAttribute("buyCart", buyCart);
						
						
						//正常
						return "product/productOrder";
					}
					
				}else{
					return "redirect:/control/shopping/buyCart.shtml";
				}
			}else{
				return "redirect:/control/shopping/buyCart.shtml";
			}
		}
	@Autowired
	private SkuService skuService;
}

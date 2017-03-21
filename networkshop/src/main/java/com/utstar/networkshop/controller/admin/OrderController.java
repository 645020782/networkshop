package com.utstar.networkshop.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.utstar.networkshop.domain.Addr;
import com.utstar.networkshop.domain.Detail;
import com.utstar.networkshop.domain.Order;
import com.utstar.networkshop.service.AddrService;
import com.utstar.networkshop.service.DetailService;
import com.utstar.networkshop.service.OrderService;


/**
 * 订单列表
 * 订单查看
 * @author lx
 *
 */
@Controller
@RequestMapping(value="/control")
public class OrderController {

	@Autowired
	private OrderService orderService;
	@Autowired
	private DetailService detailService;
	@Autowired
	private AddrService addrService;
	//订单列表
	//支付状态
	//订单状态
	@RequestMapping(value = "/order/list.do")
	public String list(Integer isPaiy,Integer state,ModelMap model){
		
		Order order = new Order();
		//支付状态
		if(null != isPaiy){
			order.setIsPaiy(isPaiy);
		}
		//订单状态
		if(null != state){
			order.setState(state);
		}
		
		List<Order> orders = orderService.getOrderList(order);
		
		model.addAttribute("orders", orders);
		
		return "order/list";
	}
	//订单查看
	@RequestMapping(value = "/order/view.do")
	public String view(Integer id,ModelMap model){
		//查询订单主表
		Order order = orderService.getOrderByKey(id);
		//查询订单详情表
		Detail  detail = new Detail();
		detail.setOrderId(id);
		List<Detail> details = detailService.getDetailList(detail);
		
		//收货地址
		Addr addr = new Addr();
		addr.setBuyerId(order.getBuyerId());
		addr.setIsDef(1);
		
		List<Addr> addrs = addrService.getAddrList(addr);
		
		//
		model.addAttribute("order", order);
		model.addAttribute("addr", addrs.get(0));
		model.addAttribute("details", details);
		
		return "order/view";
	}
}

package webProgrammingProject.app.controller;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import webProgrammingProject.app.model.Cart;
import webProgrammingProject.app.model.Category;
import webProgrammingProject.app.model.CategoryId;
import webProgrammingProject.app.model.Order;
import webProgrammingProject.app.model.Product;
import webProgrammingProject.app.model.SingleOrderItem;
import webProgrammingProject.app.service.AppService;
import webProgrammingProject.app.validator.OrderValidator;


@Controller
public class AppController {
	@Autowired
	AppService service;
	
	@Autowired
	OrderValidator ordv;
	
	@RequestMapping("/")
	public ModelAndView viewProducts(HttpSession session) {
		/*
		 * REMEMBER: HTTP Error Handling, redirection and error messages.
		 */
		createCart(session);


		ModelAndView mv = new ModelAndView("products");
		
		List<Product> products = service.findAllProducts();
		List<Category> categories = service.findAllCategories();
		
		CategoryId cId = new CategoryId();
		cId.setCategoryId(0);
		
		mv.addObject("products",products);
		mv.addObject("categories",categories);
		mv.addObject("cId",cId);
		mv.addObject("headerText","AllProducts");
		
		return mv;
	}
	
	@RequestMapping(value = "/products", method = RequestMethod.GET)
	public ModelAndView changeProducts(@Valid @ModelAttribute(name="categoryId") CategoryId cId, HttpSession session) {
		/*
		 * REMEMBER: HTTP Error Handling, redirection and error messages.
		 */
		createCart(session);

		
		ModelAndView mv = new ModelAndView("products");
		
		List<Product> products = service.findByCategoryId(cId.getCategoryId());
		List<Category> categories = service.findAllCategories();
		
		mv.addObject("products",products);
		mv.addObject("categories",categories);
		mv.addObject("cId",cId);
		mv.addObject("headerText", service.findCategoryTitle(cId.getCategoryId()));
		
		return mv;
	}
	
	
	@RequestMapping("/product/{pid}")
	public ModelAndView displayProductPage(@PathVariable("pid") long pid, HttpSession session) {
		/*
		 * REMEMBER: HTTP Error Handling, redirection and error messages.
		 */
		createCart(session);
		
		ModelAndView mv = new ModelAndView("product");
		
		Product product = service.findSingleProductById(pid);
		if(product == null) {
			//TODO:
			System.out.println("product is null");
		}else {			
			mv.addObject("product",product);
		}
		SingleOrderItem soi = new SingleOrderItem();
		
		soi.setProduct(product);
		soi.setQuantity(0);
		mv.addObject("soi",soi);
		return mv;
	}
	
	@RequestMapping(value = "/buy/{pid}", method = RequestMethod.GET)
	public ModelAndView buyProduct(
			@ModelAttribute("soi") SingleOrderItem soi,
			@PathVariable("pid") long pid,
			HttpSession session) {
		/*
		 * REMEMBER: HTTP Error Handling, redirection and error messages.
		 */
		
		System.out.println("###################");
		System.out.println(soi.getTitle());
		System.out.println(soi.getQuantity());
		System.out.println("###################");

		createCart(session);
		

		Cart cart = (Cart) session.getAttribute("cart");
		cart.addItem(soi);
		
		ModelAndView mv = new ModelAndView("cart");
		Order order = new Order();
		
		mv.addObject("cart",cart);
		mv.addObject("order",order);		
		return mv;
	}
	
	
	public void createCart(HttpSession session) {
		if(session.getAttribute("cart") == null) {
			Cart cart = new Cart();
			session.setAttribute("cart", cart);
		}
	}
	
	
	@RequestMapping("/updatequantity")
	public ModelAndView updateQuantity(
			@RequestParam("pid") long pid,
			@RequestParam("qnt") int qnt,
			HttpSession session
			){
		createCart(session);
		Cart cart = (Cart) session.getAttribute("cart");
		ModelAndView mv = new ModelAndView("cart");
		
		cart.updateItemQuantity(pid, qnt);
		Order order = new Order();
		
		mv.addObject("cart",cart);
		mv.addObject("order",order);
		return mv;
	}
	
	@RequestMapping("/cart")
	public ModelAndView displatCart(
			HttpSession session
			){
		createCart(session);
		
		Cart cart = (Cart) session.getAttribute("cart");
		ModelAndView mv = new ModelAndView("cart");
		Order order = new Order();
		
		mv.addObject("cart",cart);
		mv.addObject("order",order);		
		return mv;
	}
	@RequestMapping("/purchase")
	public ModelAndView purchase(
			@Valid @ModelAttribute Order order,
			HttpSession session,
			BindingResult result
			){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
		LocalDateTime dateTime = LocalDateTime.now();
		String formattedDateTime = dateTime.format(formatter); // "1986-04-08 12:30"		
		order.setOrderTime(LocalDateTime.parse(formattedDateTime));
		Cart cart = (Cart) session.getAttribute("cart");
		ordv.validate(order, result);
		
		ModelAndView mv = new ModelAndView();
		if(result.hasErrors()) {
			mv.setViewName("cart");
			mv.addObject("cart",cart);
		}else {
			mv.setViewName("order-result");
			order.setItems(cart.jsonizeCart());
			
		}
		mv.addObject("order",order);
		return mv;
	}
	
}

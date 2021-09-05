package webProgrammingProject.app.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.naming.Binding;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
		List<Category> categories = service.findAllCategoriesAlphabetic();
		
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
		List<Category> categories = service.findAllCategoriesAlphabetic();
		
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
		mv.addObject("product",product);

		SingleOrderItem soi = new SingleOrderItem();
		
		soi.setProduct(product);
		soi.setQuantity(0);
		mv.addObject("soi",soi);
		return mv;
	}
	
	@RequestMapping(value = "/buy/{pid}", method = RequestMethod.GET)
	public ModelAndView buyProduct(
			@Valid @ModelAttribute("soi") SingleOrderItem soi,
			BindingResult result,
			@PathVariable("pid") long pid,
			HttpSession session) {
		/*
		 * REMEMBER: HTTP Error Handling, redirection and error messages.
		 */
		ModelAndView mv = new ModelAndView();

		createCart(session);
		
		if(result.hasErrors()) {
			mv.setViewName("product");
			mv.addObject("soi",soi);
			mv.addObject("product",soi.getProduct());
		}else {
			Cart cart = (Cart) session.getAttribute("cart");
			cart.addItem(soi);
			mv.setViewName("cart");
			
			
			Order order = new Order();
			
			mv.addObject("cart",cart);
			mv.addObject("order",order);		
		}

		
		return mv;
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
	
	//////////////////////////////ORDER CONTROL////////////////////////////////////
	@RequestMapping("/purchase")
	public ModelAndView purchase(
			@Valid @ModelAttribute Order order,
			BindingResult result,
			HttpSession session
			){

		LocalDateTime now = now();
		order.setOrderTime(now);
		Cart cart = (Cart) session.getAttribute("cart");
		ordv.validate(order, result);

		ModelAndView mv = new ModelAndView();
		if(result.hasErrors()) {
			System.err.println("we have errors");

			for(FieldError fe : result.getFieldErrors()) {
				System.err.println(fe);
			}
			mv.setViewName("cart");
			mv.addObject("cart",cart);
		}else {
			System.err.println("ELSE");

			order.setStatus("processing");			
			order.setItems(cart.jsonizeCart());
			order.setTotalPrice(cart.getCartTotal());
			service.saveOrder(order);
			mv.addObject("soilist",cart.getItems());
			mv.setViewName("order-result");
			session.removeAttribute("cart");
		}
		
		
		System.err.println("we passed if case?");

		mv.addObject("order",order);
		return mv;
	}

	@RequestMapping("/order/{oid}")
	public ModelAndView orderDetails(@PathVariable(name="oid") long oid) {
		ModelAndView mv = new ModelAndView("order-result");
		Order order = service.findOrderById(oid);
		List<SingleOrderItem> soiList = service.JsonToSOIList(order.getItems());
		mv.addObject("order",order);
		mv.addObject("soilist",soiList);
		return mv;
	}
	
	@RequestMapping("/cancelorder/{oid}")
	public ModelAndView cancelOrder(@PathVariable(name="oid") long oid) {
		ModelAndView mv = new ModelAndView("order-result");
		Order order = service.findOrderById(oid);
		order.setStatus("cancelled");
		service.saveOrder(order);
		mv.addObject("order",order);
		
		return mv;
	}
	
	
	//////////////////////////////////////////////ADMIN FUNCTINOS/////////////////////////////////////
	@RequestMapping("/admin")
	public ModelAndView displayAdminMenu() {
		ModelAndView mv = new ModelAndView("admin-panel");
		
		return mv;
		
	}
	
	@RequestMapping("/admin/productform")
	public ModelAndView displayAddProduct() {
		ModelAndView mv = new ModelAndView("addProduct");
		Product p = new Product();
		List<Category> categories = service.findAllCategoriesAlphabetic();
		CategoryId cId = new CategoryId();
		cId.setCategoryId(0);
		
		mv.addObject("product", p);
		mv.addObject("categoryID", cId);
		mv.addObject("categories",categories);
		
		return mv;
	}
	@RequestMapping("/admin/addCategory")
	public ModelAndView displayAddCategory() {
		ModelAndView mv = new ModelAndView("addCategory");
		Category c = new Category();
		mv.addObject("c", c);
		return mv;
	}
	@RequestMapping("/admin/addCategory/{id}")
	public ModelAndView addCategory(@ModelAttribute(name = "c") Category c, 
		BindingResult result) {
		ModelAndView mv = new ModelAndView();
		if(result.hasFieldErrors()) {
			mv.setViewName("addCategory");
		}else {
			mv.setViewName("admin-panel");
			service.saveCategory(c);
		}
		
		return mv;
		
		
	}
	
	@RequestMapping(value="/admin/addproduct")
	public ModelAndView addProduct(
			@Valid @ModelAttribute(name="product") Product product,
			BindingResult result,
			@Valid @ModelAttribute(name="categoryID") CategoryId categoryID,
			BindingResult reult1
			) {

		for(FieldError fe : result.getFieldErrors()) {
			System.err.println(fe);
		}
		
		ModelAndView mv = new ModelAndView();
		if(result.hasErrors()) {
			mv.setViewName("admin-panel");
			List<Category> categories = service.findAllCategoriesAlphabetic();
			mv.addObject("product", product);
			mv.addObject("categoryId", categoryID);
			mv.addObject("categories",categories);
			
		}else {
			mv.setViewName("product");
			Category c = service.getCategoryByID(categoryID.getCategoryId());
			product.setCategory(c);
			mv.addObject("product",product);
			SingleOrderItem soi = new SingleOrderItem();
			soi.setProduct(product);
			soi.setQuantity(0);
			mv.addObject("soi",soi);
			service.saveProduct(product);
		}
		
		return mv;
	}
	
	/////////////////////////////ORDER LISTING//////////////////////////////
	@RequestMapping("/all-orders/all/{listingOpt}")
	public ModelAndView allOrders(
			@PathVariable("listingOpt") String listingOpt) {
		
		List<Order> orders;
		ModelAndView mv = new ModelAndView("all-orders");

		
		if(listingOpt.equals("standart")) {
			orders = service.findAllOrders();
		}else if(listingOpt.equals("desc")) {
			orders = service.orderOrdersByOrderDateDESC();

		}else if(listingOpt.equals("asc")){
			orders = service.orderOrdersByOrderDateASC();

		}else {
			orders = service.findOrdersByEmail(listingOpt);
		}		
		mv.addObject("orders", orders);
		return mv;
	}
	
	@RequestMapping("/all-orders/filterEmail/{email}")
	public ModelAndView allOrdersEmail(
			@PathVariable("email") String email) {
		
		List<Order> orders = service.findOrdersByEmail(email);
		ModelAndView mv = new ModelAndView();
		
		return mv;
	}
	
	///////////////////////////FREE FUNCTIONS///////////////////////////////////
	public void createCart(HttpSession session) {
		if(session.getAttribute("cart") == null) {
			Cart cart = new Cart();
			session.setAttribute("cart", cart);
		}
	}
	
	private LocalDateTime now() {
		System.err.println("Getting current time");
		System.err.println("Creating Formatter");

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
		
		System.err.println("Creating datetime");
		LocalDateTime dateTime = LocalDateTime.now();

		System.err.println("dateTimeformat(formatter)");
		String n = dateTime.format(formatter); 
		return LocalDateTime.parse(n,formatter);
	}
	
	
}

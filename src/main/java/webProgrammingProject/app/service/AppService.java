package webProgrammingProject.app.service;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import webProgrammingProject.app.dao.CategoryRepository;
import webProgrammingProject.app.dao.OrderRepository;
import webProgrammingProject.app.dao.ProductRepository;
import webProgrammingProject.app.model.Cart;
import webProgrammingProject.app.model.Category;
import webProgrammingProject.app.model.Order;
import webProgrammingProject.app.model.Product;
import webProgrammingProject.app.model.SingleOrderItem;

@Service
@Transactional
public class AppService {
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	OrderRepository orderRepository;
	
	public Category getCategoryByID(Long id) {
		
		return categoryRepository.getCatogryById(id);
	}
	
	public List<Product> findAllProducts(){
		return productRepository.findAll();
	}
	
	
	public List<Category> findAllCategoriesAlphabetic(){
		return categoryRepository.findAllAlphabetic();
	}
	
	public List<Product> findByCategoryId(long cId){
		return productRepository.findByCategoryId(cId);
	}
	
	public String findCategoryTitle(long cId) {
		return categoryRepository.findCategoryTitle(cId);
	}
	
	public Product findSingleProductById(long pid){
		return productRepository.findSingleProductById(pid);
	}
	
	public long countOrders() {
		return orderRepository.count();
	}
	
	public void saveOrder(Order order) {
		orderRepository.save(order);
	}
	public void saveCategory(Category cat) {
		categoryRepository.save(cat);
	}
	
	public List<Order> findAllOrders(){
		return orderRepository.findAll();
	}
	
	public void deleteOrderById(long id) {
		orderRepository.deleteById(id);
	}
	
	public List<Order> orderOrdersByOrderDateASC(){
		return orderRepository.orderByOrderDateASC();
	}
	
	public List<Order> orderOrdersByOrderDateDESC(){
		return orderRepository.orderByOrderDateDESC();
	}
	public Order findOrderById(long id) {
		return orderRepository.findById(id);
	}
	
	public void saveProduct(Product p) {
		productRepository.save(p);
	}
	
	public List<Order> findOrdersByEmail(String email){
		return orderRepository.findByEmail(email);
	}
	public List<SingleOrderItem> JsonToSOIList(String json) {
		JSONArray ja = new JSONArray(json);
		List<SingleOrderItem> tempsoi = new ArrayList<>();
		for(int i = 0; i < ja.length(); i++)
		{
		      JSONObject object = ja.getJSONObject(i);
		      Long id = (long) object.get("id");
		      Integer qnt = (int) object.getInt("qnt");
		      Product p = findSingleProductById(id);
		      
		      SingleOrderItem soi = new SingleOrderItem();
		      soi.setProduct(p);
		      soi.setQuantity(qnt);
		      tempsoi.add(soi);
		      System.err.println(object);
		}
		return tempsoi;
	}
} 

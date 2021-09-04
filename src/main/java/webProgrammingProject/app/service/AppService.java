package webProgrammingProject.app.service;

import java.util.List;

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

@Service
@Transactional
public class AppService {
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	OrderRepository orderRepository;
	
	public List<Product> findAllProducts(){
		return productRepository.findAll();
	}
	
	public List<Category> findAllCategories(){
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
	
	public List<Order> findAllOrders(){
		return orderRepository.findAll();
	}
	
	public void deleteOrderById(long id) {
		orderRepository.deleteById(id);
	}
	
	public List<Order> findAllOrdersWithEmail(String email){
		return orderRepository.findAllWithEmail(email);
	}
	
	public List<Order> orderOrdersByOrderDateASC(){
		return orderRepository.orderByOrderDateASC();
	}
	
	public List<Order> orderOrdersByOrderDateDESC(){
		return orderRepository.orderByOrderDateDESC();
	}
} 

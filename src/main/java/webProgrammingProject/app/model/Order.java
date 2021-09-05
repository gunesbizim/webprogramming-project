package webProgrammingProject.app.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import webProgrammingProject.app.service.AppService;


@Entity
@Table(name="orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name = "id")
	private long id;
	
	@Size (min = 5, max = 50)
	@Column (name = "name")
	private String name;
	
	@Email
	@Size(max=100)
	@NotEmpty
	@Column(name="email")
	private String email;
	
	@Size (min = 15, max = 250)
	@Column (name = "adress")
	private String adress;
	
	@Future
	@Column(name = "preferreddate")
	private LocalDate _preferredDate;
	
	@Transient
	@NotEmpty
	private String preferredDate;
	
	@DateTimeFormat(pattern = "dd.MM.yyyy HH:mm")
	@Column(name="ordertime")
	private LocalDateTime orderTime;
	
	@NotEmpty
	@Size(min=10,max=17)
	@Column(name="phone")
	private String phone;
	
	@Column(name="items")
	private String items;
	
	@Column(name="process_status ")
	private String status;
	
	@Column(name="totalprice")
	private double totalPrice;
	
	@Transient
	private List<Product> productList;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public LocalDate get_preferredDate() {
		return _preferredDate;
	}

	public void set_preferredDate(LocalDate _preferredDate) {
		this._preferredDate = _preferredDate;
	}

	public String getPreferredDate() {
		return preferredDate;
	}

	public void setPreferredDate(String preferredDate) {

		if(!preferredDate.equals("")) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
			System.err.println("to local date");
			_preferredDate = LocalDate.parse(preferredDate,formatter);
		}
		System.err.println("date parsed");

		this.preferredDate = preferredDate;
	}

	public LocalDateTime getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(LocalDateTime orderTime) {
		this.orderTime = orderTime;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getItems() {
		return items;
	}

	public void setItems(String items) {		
		this.items = items;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public boolean isProcessing() {
		System.err.println(status.equals("processing"));
		return status.equals("processing");
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public List<Product> getProductList() {
		return productList;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}
	
	
}

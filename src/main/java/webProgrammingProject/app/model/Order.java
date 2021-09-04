package webProgrammingProject.app.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.json.JSONObject;
import org.springframework.format.annotation.DateTimeFormat;


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
	
	@Size (min = 15, max = 50)
	@Column (name = "adress")
	private String adress;
	
	@FutureOrPresent
	@Column(name = "preferreddate")
	private LocalDate preferredDate;
	
	@DateTimeFormat(pattern = "dd.MM.yyyy HH:mm")
	@Column(name="ordertime")
	private LocalDateTime orderTime;
	
	@Size(min=10,max=17)
	@Column(name="phone")
	private String phone;
	
	@NotNull
	@Column(name="items")
	private JSONObject[] items;
	


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

	public LocalDate getPreferredDate() {
		return preferredDate;
	}

	public void setPreferredDate(LocalDate preferredDate) {
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
	
	public JSONObject[] getItems() {
		return items;
	}

	public void setItems(JSONObject[] items) {
		this.items = items;
	}

	
}

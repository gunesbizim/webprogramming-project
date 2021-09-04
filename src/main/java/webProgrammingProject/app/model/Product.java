package webProgrammingProject.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Entity
@Table(name = "products")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name = "id")
	private long id;
	
	@Size (min = 3, max = 50)
	@Column (name = "title")
	private String title;
	
	@Size (min = 4, max = 100)
	@Column (name = "productimage")
	private String productImage;
	
	@Size (min = 20, max = 1000)
	@Column (name = "productdescription")
	private String productDescription;
	
	
	@Min (value = 0)
	@Max (value = 9999999)
	@Column (name = "price")
	private double price;
	/*
	@Column (name="categoryId")
	private String categoryId;
	*/
	
	@ManyToOne
	@JoinColumn(name="category_id", nullable=false)
	private Category category;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getProductImage() {
		return productImage;
	}

	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
}

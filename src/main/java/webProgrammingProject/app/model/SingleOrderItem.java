package webProgrammingProject.app.model;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class SingleOrderItem {

	
	@NotNull
	private Product product;
	
	@Min(value=1)
	private Integer quantity;
	
	private double totalProductPrice;
	
	public void setProduct(Product product) {
		System.out.println("----product is set----");
		System.out.println(product);
		this.product = product;
		System.out.println(this.product);

	}
		
	public void setQuantity(int quantity) {
		System.out.println("----quantity is set----");
		System.out.println(quantity);
		
		this.quantity = quantity;
		System.out.println(this.quantity);
		this.totalProductPrice = quantity*product.getPrice();

		//this.totalProductPrice = product.getPrice() * quantity;
	}
	
	public void setTotalProductPrice(double totalProductPrice) {
		this.totalProductPrice = totalProductPrice;
	}
	
	public int getQuantity() {
		return quantity;
	}


	public long getItemID() {
		return product.getId();
	}


	public double getTotalProductPrice() {
		return totalProductPrice;
	}
	
	public String getImageUrl() {
		return product.getProductImage();
	}
	
	public String getTitle() {
		return product.getTitle();
	}

	public Product getProduct() {
		return product;
	}

	public void addQuantity(int quantity) {
		this.quantity += quantity;
		this.totalProductPrice += product.getPrice() * quantity;
	}

}

package webProgrammingProject.app.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class Cart {
	
	private List<SingleOrderItem> items = new ArrayList<>();
	private double cartTotal;
	private int itemCount;
	public void addItem(SingleOrderItem soi) {
		printAll();
		SingleOrderItem existingItem = find(soi.getItemID());
		if(existingItem == null) {
			items.add(soi);
		}else {
			existingItem.addQuantity(soi.getQuantity());			
		}
		System.out.println("---------------------");
		printAll();
		itemCount++;
		calculateCartTotal();
		
	}
	
	public void updateItemQuantity(long id, int qnt) {
		SingleOrderItem existingItem = find(id);
		if(qnt==0) {
			deleteItem(id);
		}else {
			find(id).setQuantity(qnt);
		}
	}
	
	public SingleOrderItem find(long pid) {
		for(SingleOrderItem soi : items ){
			if(soi.getItemID() == pid) {
				return soi;
			}
		}
		return null;
	}
	public void printAll() {
		for(SingleOrderItem soi : items ){
			System.out.println(
					String.format("id: %d qnt: %d", soi.getItemID(),soi.getQuantity())
					);
		}
	}
	
	private void calculateCartTotal() {
		for(SingleOrderItem soi : items ){
			cartTotal += soi.getTotalProductPrice();
		}
	}
	public List<SingleOrderItem> getItems() {
		return items;
	}
	public void setItems(List<SingleOrderItem> items) {
		this.items = items;
	}
	public double getCartTotal() {
		return cartTotal;
	}
	public void setCartTotal(double cartTotal) {
		this.cartTotal = cartTotal;
	}
	private void deleteItem(long id) {
		items.remove(
				find(id));
		itemCount--;
	}
	public JSONObject[] jsonizeCart() {
		JSONObject finalCart []= new JSONObject[itemCount];
		int tempIndex = 0;
		for(SingleOrderItem soi : items ){
			JSONObject temp = new JSONObject();
			temp.put("id", soi.getItemID());
			temp.put("qnt", soi.getQuantity());
			finalCart[tempIndex] = temp;
		}
		
		return finalCart;
	}
}

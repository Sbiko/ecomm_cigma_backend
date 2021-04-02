package org.cigma.dev.model.request;

import java.math.BigDecimal;
import java.util.Set;



public class OrderCDTO {

	private int totalQuantity;
	

	private BigDecimal totalPrice;

	private Set<OrderItemCDTO> orderItems;

	public int getTotalQuantity() {
		return totalQuantity;
	}


	public void setTotalQuantity(int totalQuantity) {
		this.totalQuantity = totalQuantity;
	}


	public BigDecimal getTotalPrice() {
		return totalPrice;
	}


	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}


	public Set<OrderItemCDTO> getOrderItems() {
		return orderItems;
	}


	public void setOrderItems(Set<OrderItemCDTO> orderItems) {
		this.orderItems = orderItems;
	}
	
	
	
	

}

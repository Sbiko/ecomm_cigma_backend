package org.cigma.dev.model.request;

import java.math.BigDecimal;

public class OrderItemCDTO {
	
	private BigDecimal unitPrice;

	private int quantity;
	
	private Long productId;


	
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}


		

}

package org.cigma.dev.model.request;


public class Purchase {
	
	private Customer customer;
	private OrderCDTO order;
	
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public OrderCDTO getOrder() {
		return order;
	}
	public void setOrder(OrderCDTO order) {
		this.order = order;
	}

	
	

}

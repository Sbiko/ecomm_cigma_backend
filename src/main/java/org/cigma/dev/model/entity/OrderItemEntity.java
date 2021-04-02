package org.cigma.dev.model.entity;

import java.math.BigDecimal;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="orders_items")
@Getter
@Setter
@NoArgsConstructor
public class OrderItemEntity {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name="unit_price")
	private BigDecimal unitPrice;
	
	private int quantity;
	
	@Column(name="product_id")
	private Long productId;
	
	@ManyToOne
	@JoinColumn(name="order_id")
	private OrderEntity order;
	
	
	
	
}

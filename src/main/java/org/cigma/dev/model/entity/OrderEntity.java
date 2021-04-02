package org.cigma.dev.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="orders")
@Getter
@Setter
@NoArgsConstructor
public class OrderEntity implements Serializable {

	private static final long serialVersionUID = -9171408800654131062L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name="total_quantity")
	private int totalQuantity;
	
	@Column(name="total_price")
	private BigDecimal totalPrice;
	

	@Column(name="total_creation")
	@CreationTimestamp
	private Date dateCreation;
	
	@Column(name="total_updated")
	@UpdateTimestamp
	private Date lastUpdated;
	
	@OneToMany(cascade =CascadeType.ALL, mappedBy = "order")
	private Set<OrderItemEntity> orderItems = new HashSet<>();
	
	@ManyToOne
	@JoinColumn(name="customer_id")
	private UserEntity customer;
	
	public void add(OrderItemEntity item) {
		
		if(item != null) {
			if(orderItems == null) {
				orderItems = new HashSet<>();
			}
			orderItems.add(item);
			item.setOrder(this);
		}
	}
	
	
	
	
}

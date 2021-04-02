package org.cigma.dev.model.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="products")
@Getter
@Setter
@NoArgsConstructor
public class ProductEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	
	@Column(nullable=false, length=50)
	private String name;
	
	@Column(name="reference", nullable=false, unique = true)
	private String sku;
	
	@Column(name="unit_price")
	private BigDecimal unitPrice;
	
	@Column(name="units_in_stock")
	private int unitsInStock;
	
	@Column(name="date_creation")
	@CreationTimestamp
	private Date dateCreation;
	
	@Column(name="date_updated")
	@UpdateTimestamp
	private Date lastUpdated;
	
	private String productID;



	

}

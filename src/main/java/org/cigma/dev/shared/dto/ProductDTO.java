package org.cigma.dev.shared.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class ProductDTO {
	
	private Long id;
	private String sku;
	private BigDecimal unitPrice;
	private int unitsInStock;
	private String name;
	private String productID;

}

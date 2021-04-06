package org.cigma.dev.model.request;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderItemCDTO {
	
	private BigDecimal unitPrice;

	private int quantity;
	
	private String productId;




		

}

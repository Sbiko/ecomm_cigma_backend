package org.cigma.dev.model.request;

import java.math.BigDecimal;
import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class OrderCDTO {

	private int totalQuantity;
	

	private BigDecimal totalPrice;

	private Set<OrderItemCDTO> orderItems;


	
	
	
	

}

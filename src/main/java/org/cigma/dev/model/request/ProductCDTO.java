package org.cigma.dev.model.request;

import java.math.BigDecimal;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductCDTO {
	
	@NotEmpty
	private String sku;
	private BigDecimal unitPrice;

	private int unitsInStock;
	@NotEmpty
	private String name;
}

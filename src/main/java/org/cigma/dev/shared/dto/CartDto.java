package org.cigma.dev.shared.dto;



import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CartDto {
	
    private String productId;
    private String userID;
    private int quantity;
    private String cartID;

}

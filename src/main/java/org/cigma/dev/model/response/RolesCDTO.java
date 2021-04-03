package org.cigma.dev.model.response;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RolesCDTO {
	private String name;
	
	private List<AuthorityCDTO> authorities;


	
	

}

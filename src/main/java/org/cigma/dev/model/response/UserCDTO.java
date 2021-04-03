package org.cigma.dev.model.response;


import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserCDTO {
	
	private String userId;
	private String lastName;
	private String firstName;
	private String email;
	private List<RolesCDTO> roles;
	
	
	
}

package org.cigma.dev.shared.dto;

import java.io.Serializable;
import java.util.List;

import org.cigma.dev.model.response.RolesCDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDto implements Serializable {
	
	private static final long serialVersionUID = 5632790089217828079L;
	
	private long id;
	private String userId;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String encryptedPassword;
	private String phoneNumber;
	private String nickname;
	private List<RolesCDTO> roles;

	
	
}

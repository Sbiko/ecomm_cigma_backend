package org.cigma.dev.model.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PasswordResetTokenEntity implements Serializable {

	private static final long serialVersionUID = -3112351073353340938L;
	
	
	@Id
	@GeneratedValue
	private long id;
	
	private String token;
	
	@OneToOne
	@JoinColumn(name="users_id")
	private UserEntity userDetails;



}

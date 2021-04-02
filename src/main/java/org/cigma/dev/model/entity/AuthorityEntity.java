package org.cigma.dev.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="authorities")
@Getter
@Setter
@NoArgsConstructor
public class AuthorityEntity implements Serializable {

	private static final long serialVersionUID = 5004678002369549743L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(nullable = false, length= 20)
	private String name;
	
	@ManyToMany(mappedBy = "authorities")
	private Collection<RoleEntity> roles = new ArrayList<>();


	public AuthorityEntity(String name) {
		this.name = name;
	}


	
	
	

}

package org.cigma.dev.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="roles")
@Getter
@Setter
@NoArgsConstructor
public class RoleEntity implements Serializable {

	private static final long serialVersionUID = 3938884233137883696L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(nullable = false, length= 20)
	private String name;
	
	@ManyToMany(mappedBy="roles")
	private Collection<UserEntity> users;
	
	@ManyToMany(cascade= {CascadeType.PERSIST}, fetch= FetchType.EAGER)
	@JoinTable(name="roles_authorities",
			joinColumns=@JoinColumn(name="roles_id", referencedColumnName="id"),
			inverseJoinColumns=@JoinColumn(name="authorities_id", referencedColumnName="id"))
	private Collection<AuthorityEntity> authorities = new ArrayList<>();


	public RoleEntity(String name) {
		this.name = name;
	}

	
	
	
	
	

}

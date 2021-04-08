package org.cigma.dev.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.JoinColumn;

@Entity
@Table(name="users")
@Getter
@Setter
@NoArgsConstructor
public class UserEntity implements Serializable {

	private static final long serialVersionUID = 6771624715841380030L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(nullable=false)
	private String userId;
	
	@Column(nullable=false, length=50)
	private String firstName;
	
	@Column(nullable=false, length= 50)
	private String lastName;
	
	@Column(nullable=false, length=120, unique = true)
	private String email;
	
	@Column(nullable=false)
	private String encryptedPassword;
	
	@Column(nullable=false)
	private String phoneNumber;
	
	@Column(nullable=false, unique=true)
	private String nickname;
	
	@OneToMany(mappedBy= "customer", cascade = CascadeType.ALL)
	private Set<OrderEntity> orders = new HashSet<>();

	
	@ManyToMany(cascade= {CascadeType.PERSIST}, fetch= FetchType.EAGER)
	@JoinTable(name="users_roles",
			joinColumns=@JoinColumn(name="users_id", referencedColumnName="id"),
			inverseJoinColumns=@JoinColumn(name="roles_id", referencedColumnName="id"))
	private Collection<RoleEntity> roles = new ArrayList<>();
	
	
	
	public void add(OrderEntity order) {
		if(order != null) {
			if(orders == null) {
				orders = new HashSet<>();
			}
			orders.add(order);
			order.setCustomer(this);
		}
	}



	@Override
	public String toString() {
		return "UserEntity [id=" + id + ", userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", email=" + email  + "]";
	}

	
	
	
}

package com.project.shopping.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
import javax.persistence.OneToMany;

import org.hibernate.annotations.Columns;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
//@ToString
public class User implements UserDetails {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  
  private String name;
  
  @Column(unique= true)
  private String useremailId;
  
  private String mobileno;
  private String password;
  private String userAddress;
  @JsonFormat(pattern="yyyy-MM-dd")
  private Date DOB;
  



@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
private List<Products> product = new ArrayList<>();
  
@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
private List<addToCart> addtocart = new ArrayList<>(); ;

@ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER )
@JoinTable(name="user_role",
joinColumns=@JoinColumn(name="user",referencedColumnName="id"),
inverseJoinColumns =@JoinColumn(name="role",referencedColumnName ="id")
		)
private Set<Role> roles = new HashSet<>();

@Override
public Collection<? extends GrantedAuthority> getAuthorities() {
	List<SimpleGrantedAuthority> authorities = this.roles.stream().map((role) -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	return authorities;
}



@Override
public String getUsername() {
	// TODO Auto-generated method stub
	return this.useremailId;
}

@Override
public boolean isAccountNonExpired() {
	// TODO Auto-generated method stub
	return true;
}

@Override
public boolean isAccountNonLocked() {
	// TODO Auto-generated method stub
	return true;
}

@Override
public boolean isCredentialsNonExpired() {
	// TODO Auto-generated method stub
	return true;
}

@Override
public boolean isEnabled() {
	// TODO Auto-generated method stub
	return true;
}
}

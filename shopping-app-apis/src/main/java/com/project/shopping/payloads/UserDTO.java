package com.project.shopping.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
	  private int id;
	  
	  @NotEmpty
	  @Size(min = 4,message = "Name should be greater than 4")
	  private String name;
	  
	  @Email(message = "Email address is not valid!")
	  @Column(unique = true)
	  private String useremailId;
	  
	  @NotEmpty
	  @Size(min =5, max = 16, message = "Pasword must be greater than 5 and smaller than 16")
	  private String password;
	  
	  private String mobileno;
	  private String userAddress;

	  private Set<RoleDto> roles = new HashSet<>();
	  
	  @JsonFormat(pattern="yyyy-MM-dd")
	  private Date DOB;
	  
	  
}

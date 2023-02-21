package com.project.shopping.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.shopping.payloads.ApiResponse;
import com.project.shopping.payloads.UserDTO;
import com.project.shopping.service.UserService;

@RestController
@RequestMapping("api/users")
public class UserController {
	
		@Autowired
	private UserService userservice;
		//Post- Create User
	@PostMapping("/")
		public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO)
	{
		UserDTO createUserDTO = this.userservice.createUser(userDTO);
		return new ResponseEntity<>(createUserDTO,HttpStatus.CREATED);
	}
	 
	//Put - update user
	@PutMapping("/{id}")
	public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO , @PathVariable Integer id)
	{
		UserDTO updateUserDTO= this.userservice.updateUser(userDTO, id);
		return new ResponseEntity<>(updateUserDTO,HttpStatus.OK);
	}
	//Delete - delete user
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer id)
	{
		this.userservice.deleteUser(id);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted successfully", true), HttpStatus.OK);
	}
	
	//get - to get user
	@GetMapping("/")
	public ResponseEntity<List<UserDTO>> getAllUsers()
	{
		return ResponseEntity.ok(this.userservice.listOfUsers());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> getUserById(@PathVariable Integer id)
	{
		return ResponseEntity.ok(this.userservice.findUserById(id));
	}
	
}

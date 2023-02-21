package com.project.shopping.service;

import java.util.List;

import com.project.shopping.payloads.UserDTO;


public interface UserService {
	
	UserDTO registerUser(UserDTO userDto);
	UserDTO createUser(UserDTO userDTO);
	UserDTO updateUser(UserDTO userDTO, int id);
	void deleteUser(int id);
	UserDTO findUserById(int id);
	List<UserDTO> listOfUsers();
	
	
}

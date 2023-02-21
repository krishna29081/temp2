package com.project.shopping.ServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.shopping.config.AppConstants;
import com.project.shopping.entity.Role;
import com.project.shopping.entity.User;
import com.project.shopping.payloads.UserDTO;
import com.project.shopping.repo.Rolerepo;
import com.project.shopping.repo.UserRepo;
import com.project.shopping.service.UserService;
import com.project.shopping.exceptions.*;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepo userrepo;
	
	@Autowired
	private ModelMapper modelmapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private Rolerepo rolerepo;
	
	@Override
	public UserDTO createUser(UserDTO userDTO) {
		User user = this.DTOtoUser(userDTO);
		User savedUser = userrepo.save(user);
		return userToDto(savedUser);
	}

	@Override
	public UserDTO updateUser(UserDTO userDTO, int id) {
		
	 User user = this.userrepo.findById(id)
			 .orElseThrow(()-> new ResourceNotFoundException("User", "id", id));
		user.setDOB(userDTO.getDOB());
		user.setName(userDTO.getName());
		user.setUserAddress(userDTO.getUserAddress());
		user.setUseremailId(userDTO.getUseremailId());
		user.setPassword(userDTO.getPassword());
	User updatedUser = userrepo.save(user);
	 
	return userToDto(updatedUser) ;
	}

	@Override
	public void deleteUser(int id) {
		User user = this.userrepo.findById(id)
				 .orElseThrow(()-> new ResourceNotFoundException("User", "id", id));
		userrepo.delete(user);
	}

	@Override
	public UserDTO findUserById(int id) {
		User user = this.userrepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User", " id ", id));
		return userToDto(user);
	}

	@Override
	public List<UserDTO> listOfUsers() {
		List<User> users = this.userrepo.findAll();
//		List<UserDTO> userDTO = null;	
//		for(User tempuser : user )
//		{ 
//			UserDTO newUser = userToDto(tempuser);
//			userDTO.add(newUser);
//		}
		
		List<UserDTO> userDTO = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
		return userDTO;//userDTO;
	}
	
	private User DTOtoUser(UserDTO userDTO)
	{
		User user = this.modelmapper.map(userDTO, User.class);
		
//		user.setId(userDTO.getId());
//		user.setDOB(userDTO.getDOB());
//		user.setName(userDTO.getName());
//		user.setUserAddress(userDTO.getUserAddress());
//		user.setUseremailId(userDTO.getUseremailId());
//		user.setUserPassword(userDTO.getUserPassword());
		return user;
	}
	
	private UserDTO userToDto(User user)
	{
		UserDTO userDTO= this.modelmapper.map(user, UserDTO.class);
		return userDTO;
	
	}

	@Override
	public UserDTO registerUser(UserDTO userDto) {
		User user = modelmapper.map(userDto,User.class);
		//encoded password
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		//roles
		Role roleOfuser = rolerepo.findById(AppConstants.NORMAL_USER).get();
		
		user.getRoles().add(roleOfuser);
		User newUser = userrepo.save(user);
		
		return modelmapper.map(newUser, UserDTO.class);
	}

}

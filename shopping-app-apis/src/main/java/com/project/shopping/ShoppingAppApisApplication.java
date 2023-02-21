package com.project.shopping;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.project.shopping.config.AppConstants;
import com.project.shopping.entity.Role;
import com.project.shopping.repo.Rolerepo;

@SpringBootApplication
public class ShoppingAppApisApplication implements CommandLineRunner {
	
//	@Autowired
//	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private Rolerepo rolerepo;
	
	@Override
	public void run(String... args) throws Exception {
//	System.out.println(this.passwordEncoder.encode("xyz"));
		try {
			Role role = new Role();
			role.setId(AppConstants.ADMIN_USER);
			role.setName("ROLE_ADMIN");
			

			Role role1 = new Role();
			role1.setId(AppConstants.NORMAL_USER);
			role1.setName("ROLE_NORMAL");
			
			List<Role> listOFRoles = List.of(role,role1);
			
			List<Role> saveAllRoles = rolerepo.saveAll(listOFRoles);
			
			saveAllRoles.forEach(r -> {
				System.out.println(r.getName());
			});
		
		
		} catch (Exception e) {
				e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		SpringApplication.run(ShoppingAppApisApplication.class, args);
	}

    @Bean
    ModelMapper modelmapper() {
        return new ModelMapper();
    }



}

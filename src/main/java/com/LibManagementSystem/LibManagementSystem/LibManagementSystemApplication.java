package com.LibManagementSystem.LibManagementSystem;

import com.LibManagementSystem.LibManagementSystem.models.UserRelated.User;
import com.LibManagementSystem.LibManagementSystem.models.UserRelated.UserRole;
import com.LibManagementSystem.LibManagementSystem.repo.UserRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class LibManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibManagementSystemApplication.class, args);
	}


	@Value("${application.env.creds.admin-email}")
	private String ADMIN_EMAIL;

	@Value("${application.env.creds.admin-pass}")
	private String ADMIN_PASSWORD;
	//admin user

	@Bean
	CommandLineRunner run(UserRepo userRepo, PasswordEncoder passwordEncoder){
		return args ->{
			if(userRepo.findByEmail(ADMIN_EMAIL).isPresent()){

				return;
			}


			User admin = User.builder().firstName("admin")
					.lastName("user")
					.email(ADMIN_EMAIL)
					.password(passwordEncoder.encode(ADMIN_PASSWORD))
					.userRole(UserRole.ADMIN).build();
			userRepo.save(admin);
		};
	}
}

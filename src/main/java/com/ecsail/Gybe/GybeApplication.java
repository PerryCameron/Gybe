package com.ecsail.Gybe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GybeApplication {

	public static void main(String[] args) {
		SpringApplication.run(GybeApplication.class, args);
	}

//	@Bean
//	CommandLineRunner run(AuthenticationRepository authenticationRepository, PasswordEncoder passwordEncoder){
//		return args -> {
//			RoleDTO adminRole = authenticationRepository.saveAuthority("ADMIN");
//			RoleDTO userRole = authenticationRepository.saveAuthority("USER");
//			Set<RoleDTO> roles = new HashSet<>();
//			UserDTO adminUser = authenticationRepository.saveUser(new UserDTO(0,"Perry", passwordEncoder.encode("password"),0));
//			authenticationRepository.saveUserRole(adminUser,adminRole);
//			authenticationRepository.saveUserRole(adminUser,userRole);
//
//		};
//	}

}

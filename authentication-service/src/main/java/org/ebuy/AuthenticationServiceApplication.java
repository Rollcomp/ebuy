package org.ebuy;

import org.ebuy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

@SpringBootApplication
@EnableAuthorizationServer
@EnableWebSecurity
public class AuthenticationServiceApplication implements CommandLineRunner{
	@Autowired
	private UserRepository userRepository;


	public static void main(String[] args) {
		SpringApplication.run(AuthenticationServiceApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
		/*User user = new User();
		user.setEmail("test@ebuy.org");
		user.setPassword("test@ebuy.org");
		userRepository.save(user);
		*/
	}
}

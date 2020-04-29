package org.ebuy;

import org.ebuy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

@SpringBootApplication
@EnableAuthorizationServer
@EnableWebSecurity
@EnableDiscoveryClient
public class AuthenticationServiceApplication {
    @Autowired
    private UserRepository userRepository;


    public static void main(String[] args) {
        SpringApplication.run(AuthenticationServiceApplication.class, args);
    }

}

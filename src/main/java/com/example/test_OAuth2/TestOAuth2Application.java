package com.example.test_OAuth2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
//@EnableJpaAuditing        // PasswordEncoder Bean을 찾지 못한대서... 등록
//@SpringBootApplication(scanBasePackages ={ "org.springframework.security.crypto.password.PasswordEncoder"})
public class TestOAuth2Application {
	public static void main(String[] args) {
		SpringApplication.run(TestOAuth2Application.class, args);
	}
}

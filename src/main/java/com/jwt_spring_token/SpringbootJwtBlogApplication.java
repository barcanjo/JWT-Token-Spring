package com.jwt_spring_token;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Cria um recurso rest /users que pode ser acessado por qualquer usuário, válido ou não.
 * 
 * @author Bruno Arcanjo
 *
 */
@SpringBootApplication
@RestController
public class SpringbootJwtBlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootJwtBlogApplication.class, args);
	}
	
	@RequestMapping("/")
	String hello() {
		return "hello world";
	}
}

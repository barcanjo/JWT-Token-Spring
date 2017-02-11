package com.jwt_spring_token.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Cria um recurso rest /users que só pode ser acessado quando o usuário é válido.
 * 
 * @author Bruno Arcanjo
 *
 */
@RestController
public class UserController {
	
	@RequestMapping("/users") /* Maps to all HTTP actions by default (GET,POST,..)*/
	public @ResponseBody String getUsers() {
		 return "{\"users\":[{\"firstname\":\"Richard\", \"lastname\":\"Feynman\"}," +
	                "{\"firstname\":\"Marie\",\"lastname\":\"Curie\"}]}";
	}
}

package com.jwt_spring_token.security.jwt;

/**
 * Representa os dados mínimos de um usuário para autenticar um usuário;
 * 
 * @author Bruno Arcanjo
 *
 */
public class AccountCredentials {

	private String username;
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}

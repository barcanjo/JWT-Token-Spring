package com.jwt_spring_token.security.jwt;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

/**
 * Representa os dados de um usuário autenticado pelo JWT.
 * Por padrão o usuário já está autenticado.
 * 
 * @author Bruno Arcanjo
 *
 */
public class AuthenticatedUser implements Authentication {
	
	private static final long serialVersionUID = 1L;
	
	private String name;
	private boolean authenticated = true;
	
	public AuthenticatedUser(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.name;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getCredentials() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getDetails() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getPrincipal() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAuthenticated() {
		// TODO Auto-generated method stub
		return this.authenticated;
	}

	@Override
	public void setAuthenticated(boolean authenticated) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		this.authenticated = authenticated;
	}

}

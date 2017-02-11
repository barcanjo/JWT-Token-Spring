package com.jwt_spring_token.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

/**
 * Filtro que verifica em todas as requisições (exceto a de login) se a autenticação é válida.
 * Encaminha o request para ser verificado se possui no cabeçalho a autorização. 
 * 
 * @author Bruno Arcanjo
 *
 */
public class JWTAuthenticationFilter extends GenericFilterBean {
	
	/**
	 * Analisa a requisição para encaminhar ao serviço de verificação do Token e salva no contexto de segurança da aplicação. 
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		Authentication authentication = new TokenAuthenticationService().getAuthentication((HttpServletRequest) request);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		filterChain.doFilter(request, response);
	}

}

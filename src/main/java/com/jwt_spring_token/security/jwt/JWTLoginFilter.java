package com.jwt_spring_token.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Filtro utilizado para receber as requisições de login e autenticar os usuários.
 * 
 * @author Bruno Arcanjo
 *
 */
public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {
	
	/**
	 * Serviço de gerenciamento do Token
	 */
	private TokenAuthenticationService tokenAuthenticationService;
	
	// Construtor padrão que recebe a URL utilizada para autenticar um usuário e o gerenciador
	// padrão de autenticação de usuários (o mesmo configurado em WebSecurityConfig)
	protected JWTLoginFilter(String url, AuthenticationManager authenticationManager) {
		super(new AntPathRequestMatcher(url)); // define a URL para autenticação de usuários
		setAuthenticationManager(authenticationManager); // define o gerenciador de autenticação
		tokenAuthenticationService = new TokenAuthenticationService(); // cria o serviço de Token
	}
	
	/**
	 * Inicia a tentativa de autenticar um usuário, recebendo a requisição e a resposta que será enviada de volta ao usuário
	 */
	@Override
	public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
			throws AuthenticationException, IOException, ServletException {
		// Usa o databind da classe FasterXml (Jackson) e a requisição (request) para converter os dados presentes para a classe AccountCredentials (username e password)
		// que contém o usuário e a senha informada no momento da autenticação
		AccountCredentials credentials = new ObjectMapper().readValue(httpServletRequest.getInputStream(), AccountCredentials.class);
		// cria no contexto de segurança do Spring Security, com o usuário e senha informados, um objeto de UsernamePasswordAuthenticationToken (token)
		// para em seguida verificar se os dados são válidos  
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword());
		// utilizando o gerenciador de autenticação (definido em WebSecurityConfig) verifica se o usuário e senha são válidos
		// retornando um obeto Authentication 
		return getAuthenticationManager().authenticate(token);
	}
	
	/**
	 * Método chamado sempre que a tentativa de autenticação (executada em attemptAuthentication()) ocorre com sucesso 
	 */
	@Override
	protected void successfulAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain,
			Authentication authentication) throws IOException, ServletException {
		// recebe, através do objeto da classe Authentication (o mesmo retornado em attemptAuthentication()) o nome do usuário autenticado
		String name = authentication.getName();
		// executa o método addAutehtntication, do serviço de gerenciamento do Token, passando os como parâmetro a resposta ao usuário e o nome do usuário autenticado 
		tokenAuthenticationService.addAuthentication(httpServletResponse, name);
	}
}

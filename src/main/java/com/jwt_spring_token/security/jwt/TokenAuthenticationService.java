package com.jwt_spring_token.security.jwt;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Classe que gerencia o Token, determinando as reras de criação, como nomes, expiração e validações.
 * 
 * @author Bruno Arcanjo
 *
 */
public class TokenAuthenticationService {
	
	private long EXPIRATIONTIME = 1000 * 60 * 60 * 24 * 10; // determina o tempo de validade de um token válido em 10 dias
    private String secret = "G3ner@ll"; // determina a senha para ler o Token
    private String tokenPrefix = "Bearer"; // o prefixo para determinar o nome do Token
    private String headerString = "Authorization"; // o nome do parâmetro que receberá como valor o Token 
    
    /**
     * Aapós o usuário e senha serem validados com sucesso, cria o Token utilizando o nome do usuário
     * 
     * @param response A resposta ao usuário, enviada através do Filtro
     * @param username O nome do usuário autenticado que será adicionado ao Token
     */
    public void addAuthentication(HttpServletResponse response, String username) {
    	// cria o Token JWT
    	String jwt = Jwts.builder()
    				.setSubject(username) // define o nome do usuário validado
    				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME)) // define o tempo de expiração do Token
    				.signWith(SignatureAlgorithm.HS512, secret) // define o algoritmo de encriptação da senha e a chave para ler o Token
    				.compact(); // compacta o Token para reduzir seu tamanho
    	// adiciona à resposta do usuário, devolvida pelo Filtro (resposta) o cabeçalho com seu nome, prefixo e valor do Token válido
    	response.addHeader(headerString, tokenPrefix + " " + jwt);
    }
    
    /**
     * Verifica se existe um Token na requição e se o mesmo é válido.
     * Esse método é executado sempre que uma requisição (exceto a de login) é feita.
     *  
     * @param request A requisição que pode conter no cabeçalho os dados da autenticação (Token)
     * @return Um objeto da classe Authentication válido ou nulo (não válido)
     */
    public Authentication getAuthentication(HttpServletRequest request) {
    	// verifica se existe um cabeçalho Authorization (utilizado para definir o valor do Token JWT) e devolve o valor do Token JWT
    	String token = request.getHeader(headerString);
    	// Se existe um Token no cabeçalho
    	if (null != token) {
    		// inicia o processo de conversão do Token para avaliar a sua validade
    		String username = Jwts.parser()
    				.setSigningKey(secret) // utiliza a chave para ler o Token
    				.parseClaimsJws(token) // converte os dados do Token 
    				.getBody() // retorna o corpo do Token, com seus valores
    				.getSubject(); // retorna o nome do usuário do Token
    		// se o nome do usuário foi encontrado no Token
    		if (null != username) {
    			// cria um novo objeto da classe AuthenticatedUser passando o nome do usuário encontrado no Token
    			return new AuthenticatedUser(username);
    		}
    	}
    	return null;
    }
}

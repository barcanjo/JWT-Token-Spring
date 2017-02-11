package com.jwt_spring_token.security.jwt;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configurações de segurança da aplicação, que determinam quais recursos podem ou não ser acessados
 * com ou sem autenticação.
 * 
 * @author Bruno Arcanjo
 *
 */

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	/**
	 * Configura os recursos (ou URLs) e as permissões para acessá-los
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// desabilita o controle de cache
		http.headers().cacheControl();
		// desabilita o padrão csrf para que o JWT recebea as requisições para analisá-las
		http.csrf().disable()
			// configura as requisições
			.authorizeRequests()
			// as requisições para "/" são permitidas
			.antMatchers("/").permitAll()
			// as requisições para "/login" com o método POST são permitidas
			.antMatchers(HttpMethod.POST, "/login").permitAll()
			// qualquer outra requisição deve estar ser autenticada pelo JWT
			.anyRequest().authenticated()
			.and()
			// adiciona o filtro do JWTLoginFilter para a URL "/login" utilizando o gerenciador de autenticação do Spring Security
			// fazendo uso da Classe UsernamePasswordAuthenticationFilter que se baseia em usuário, senha e permissão
			.addFilterBefore(new JWTLoginFilter("/login", authenticationManager()), UsernamePasswordAuthenticationFilter.class)
			// adiciona o filtro JWTAuthenticationFilter para verificar todas as demais requisições e analisar a presença
			// do cabeçalho com os dados da autenticação (token)
			.addFilterBefore(new JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	}
	
	/**
	 * Configura o authenticationManager() (Gerenciar de autenticação padrão do Spring) com a forma em que os usuários
	 * serão validados, em memória, via banco de dados ou etc.
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// cria uma conta padrão, válida, para fazer login no sistema e obter um Token do JWT
		auth.inMemoryAuthentication()
			.withUser("admin")
			.password("password")
			.roles("ADMIN");
	}
}

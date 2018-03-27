/**
 * 
 */
package br.com.vibbra.mesalivre.seguranca;

import javax.inject.Inject;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import br.com.vibbra.mesalivre.seguranca.autenticacao.AutenticacaoMesaLivre;

/**
 * Servlet Filter para configurar o Spring Security.
 *
 */
@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = { "br.com.vibbra.mesalivre.seguranca.autenticacao",
		"br.com.vibbra.mesalivre.service", "br.com.vibbra.mesalivre.repository" })
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	
	@Inject
	private AutenticacaoMesaLivre autenticacaoMesaLivre;
	
	@Inject
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		/*auth.inMemoryAuthentication().withUser("op").password("password").roles("OPERATOR").and().withUser("admin")
				.password("password").roles("ADMIN");*/
		auth.authenticationProvider(autenticacaoMesaLivre);
	}

	protected void configure(HttpSecurity http) throws Exception {
		http.
		authorizeRequests()
			.antMatchers("/").permitAll()
			.antMatchers("/login").permitAll()
			.antMatchers("/registration").permitAll()
			.antMatchers("/resgateSenha").permitAll()
			.anyRequest()
			.authenticated().and().csrf().disable().formLogin()
			.loginPage("/login").failureUrl("/login?error=true")
			.defaultSuccessUrl("/admin/home")
			.usernameParameter("login")
			.passwordParameter("senha")
			.and().logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutSuccessUrl("/").and().exceptionHandling();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
	}

}

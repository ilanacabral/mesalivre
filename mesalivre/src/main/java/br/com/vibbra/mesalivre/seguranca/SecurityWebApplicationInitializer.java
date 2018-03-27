package br.com.vibbra.mesalivre.seguranca;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * Classe para registrar o Servlet Filter
 *
 */
public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {

	public SecurityWebApplicationInitializer() {
		super(SecurityConfig.class);
	}

}

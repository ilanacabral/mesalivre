package br.com.vibbra.mesalivre.seguranca.autenticacao;

import java.io.IOException;
import java.util.Collection;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.vibbra.mesalivre.entity.Usuario;
import br.com.vibbra.mesalivre.service.UsuarioService;

/**
 * Classe que fornece a base para cria��o de uma l�gica customizada de
 * autentica��o de usu�rios,
 *
 */
public class AutenticacaoMesaLivre implements AuthenticationProvider {

	@Inject
	private UsuarioService usuarioService;

	private String mensagem;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String password = (String) authentication.getCredentials();
		Usuario usuario = usuarioService.findUsuarioByEmail(username);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		if (usuario == null ||  !encoder.matches(password, usuario.getPassword())) {
			throw new BadCredentialsException("Dados não encontrados.");
		}
		Collection<? extends GrantedAuthority> authorities = usuario.getAuthorities();

		return new UsernamePasswordAuthenticationToken(username, password, authorities);
	}

	@Override
	public boolean supports(Class<?> arg0) {

		return true;
	}

	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			Authentication authResult) throws IOException, ServletException {
		SecurityContextHolder.getContext().setAuthentication(authResult);
		request.getSession().setAttribute("msg", mensagem);
		response.sendRedirect("/admin/home.html");
	}

	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		request.getSession().setAttribute("msg", mensagem);
		response.sendRedirect("login.html");
	}

}

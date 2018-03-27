package br.com.vibbra.mesalivre.managedbean;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.vibbra.mesalivre.email.EnviarEmail;
import br.com.vibbra.mesalivre.entity.Usuario;
import br.com.vibbra.mesalivre.service.UsuarioService;
import br.com.vibbra.mesalivre.util.ArquivoUtil;
import br.com.vibbra.mesalivre.util.TemplateUtil;

@Controller
public class LoginController {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private EnviarEmail enviarEmail;

	@Autowired
	private TemplateUtil templateUtil;

	@RequestMapping(value = { "/", "/login" }, method = RequestMethod.GET)
	public ModelAndView login() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		return modelAndView;
	}

	@RequestMapping(value = "/resgateSenha", method = RequestMethod.GET)
	public ModelAndView resgateSenha() {
		ModelAndView modelAndView = new ModelAndView();
		Usuario usuario = new Usuario();
		modelAndView.addObject("usuario", usuario);
		modelAndView.setViewName("/resgatarSenha/alterarSenha");
		return modelAndView;
	}

	@RequestMapping(value = "/resgatarSenha/alterarSenha", method = RequestMethod.GET)
	public ModelAndView alterarSenha() {
		ModelAndView modelAndView = new ModelAndView();
		Usuario usuario = new Usuario();
		modelAndView.addObject("usuario", usuario);
		modelAndView.setViewName("/resgatarSenha/alterarSenha");
		return modelAndView;
	}

	@RequestMapping(value = "/resgatarSenha/alterarSenha", method = RequestMethod.POST)
	public ModelAndView enviarEmailConfirmacao(@Valid Usuario usuario, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("usuario", usuario);

		try {

			Usuario userExists = usuarioService.findUsuarioByEmail(usuario.getEmail());
			if (userExists == null) {
				bindingResult.rejectValue("email", "error.user", "Não existe usuário cadastrado com esse e-mail.");
			}
			if (bindingResult.hasErrors()) {
				modelAndView.setViewName("/resgatarSenha/alterarSenha");
			} else {

				Map<String, Object> mapaPropriedades = new HashMap<>();
				mapaPropriedades.put("LINK", "https://localhost:8080/mesalivre/efetivarAlteracaoSenha?usuario=");
				mapaPropriedades.put("USUARIO", "");// TODO OBTER USUÃRIO DO
													// BANCO

				String textoTransformado = templateUtil.transformaTemplateString(
						ArquivoUtil.lerArquivo("./src/main/resources/emailTemplate.txt"), mapaPropriedades);

				enviarEmail.enviar(textoTransformado, usuario.getEmail(), "Mesa Livre - Alteração de Senha",
						"mesalivre@mesalivre.com.br");

				// adiciona mensagem de sucesso
				modelAndView.addObject("successMessage",
						"Email com link para efetivar alteraÃ§Ã£o de senha enviado com sucesso.");
				modelAndView.setViewName("login");
			}

		} catch (Exception e) {
			// adiciona mensagem de sucesso
			modelAndView.addObject("erroMessage", "Erro inesperado ao tentar enviar email.");
			modelAndView.setViewName("/resgatarSenha/alterarSenha");
		}

		return modelAndView;
	}

	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public ModelAndView registration() {
		ModelAndView modelAndView = new ModelAndView();
		Usuario usuario = new Usuario();
		modelAndView.addObject("usuario", usuario);
		modelAndView.setViewName("/cadastrarUsuario/registration");
		return modelAndView;
	}

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ModelAndView createNewUser(@Valid Usuario usuario, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		Usuario userExists = usuarioService.findUsuarioByEmail(usuario.getEmail());
		if (userExists != null) {
			bindingResult.rejectValue("email", "error.user", "Já existe um usuário cadastrado com esse e-mail.");
		}
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("registration");
		} else {
			//criptografa a senha
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			usuario.setSenha(encoder.encode(usuario.getSenha()));
			
			usuarioService.salvar(usuario);

			modelAndView.addObject("successMessage", "Usuário salvo com sucesso.");
			modelAndView.addObject("usuario", new Usuario());
			modelAndView.setViewName("login");

		}
		return modelAndView;
	}

	@RequestMapping(value = "/admin/home", method = RequestMethod.GET)
	public ModelAndView home() {
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Usuario usuario = usuarioService.findUsuarioByEmail(auth.getName());
		modelAndView.addObject("nomeUsuario", "Usuário: " + usuario.getNome() + " (" + usuario.getEmail() + ")");
		modelAndView.setViewName("admin/home");
		return modelAndView;
	}

}

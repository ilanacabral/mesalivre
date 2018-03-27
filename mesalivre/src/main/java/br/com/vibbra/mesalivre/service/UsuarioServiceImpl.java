package br.com.vibbra.mesalivre.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.vibbra.mesalivre.entity.Perfil;
import br.com.vibbra.mesalivre.entity.Usuario;
import br.com.vibbra.mesalivre.repository.PerfilRepository;
import br.com.vibbra.mesalivre.repository.UsuarioRepository;

@Service("usuarioService")
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private PerfilRepository perfilRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public Usuario findUsuarioByEmail(String email) {
		return usuarioRepository.findByEmail(email);
	}

	@Override
	public void salvar(Usuario usuario) {
		usuario.setSenha(bCryptPasswordEncoder.encode(usuario.getSenha()));
		usuario.setSituacao(true);
		Perfil perfil = perfilRepository.findById(usuario.getPerfil().getId());
		usuario.setPerfil(perfil);
		usuarioRepository.save(usuario);
	}

}

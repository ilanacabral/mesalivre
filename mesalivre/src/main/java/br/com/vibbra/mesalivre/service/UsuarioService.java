package br.com.vibbra.mesalivre.service;

import br.com.vibbra.mesalivre.entity.Usuario;

public interface UsuarioService {
	public Usuario findUsuarioByEmail(String email);

	public void salvar(Usuario usuario);
}

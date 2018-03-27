package br.com.vibbra.mesalivre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.vibbra.mesalivre.entity.Perfil;

@Repository("perfilRepository")
public interface PerfilRepository extends JpaRepository<Perfil, Integer> {
	Perfil findById(Integer id);

}

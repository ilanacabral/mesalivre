package br.com.vibbra.mesalivre.entity;

import java.io.Serializable;

import javax.enterprise.context.Dependent;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

/**
 * Classe que representa tabela de perfis
 *
 */
@Dependent
@Entity
@Table(name = "PERFIL")
public class Perfil implements Serializable, GrantedAuthority {

	private static final long serialVersionUID = 208521636347717886L;

	@Id
	@Column(name = "ID_PERFIL")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "NOME")
	private String authority;

	@Override
	public String getAuthority() {
		return authority;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null) {
			return false;
		}

		if (getClass() != obj.getClass()) {
			return false;
		}

		Perfil other = (Perfil) obj;

		if (this.id == null && other.id != null) {
			return false;
		} else if (!this.id.equals(other.id)) {
			return false;
		}

		return true;
	}

}

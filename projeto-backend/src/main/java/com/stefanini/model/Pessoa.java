package com.stefanini.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author joaopedromilhome
 *
 */
@Entity
@Table(name = "TB_PESSOA")
public class Pessoa implements Serializable{

	
	/**
	 * Serializacao da Classe
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ID da Tabela
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CO_SEQ_PESSOA")
	private Long id;
	/**
	 * Nome da pessoa
	 */
	@NotNull
	@Column(name = "NO_NOME")
	private String nome;
	
	/**
	 * Email da Pessoa
	 */
	@NotNull
	@Column(name = "DS_EMAIL")
	private String email;
	/**
	 * Data de Nascimento 
	 */
	@NotNull
	@Column(name = "DT_NASCIMENTO")
	private LocalDate dataNascimento; 
	/**
	 * Situacao da Pessoa
	 */
	@NotNull
	@Column(name = "ST_PESSOA")
	private Boolean situacao;

	/**
	 * Mapeamento de Enderecos Unidirecional
	 */
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name = "CO_SEQ_PESSOA",referencedColumnName = "CO_SEQ_PESSOA")
	private Set<Endereco> enderecos = new HashSet<>();

	/**
	 * Mapeamento de Perfis Unidirecional
	 */
	@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinTable(
			name = "TB_PESSOA_PERFIL",
			joinColumns = {@JoinColumn(name = "CO_SEQ_PESSOA")},
			inverseJoinColumns = {@JoinColumn(name = "CO_SEQ_PERFIL")}
	)
	private Set<Perfil> perfils = new HashSet<>();
	/**
	 * Metodo construtor da classe
	 */
	public Pessoa() {
	}


	public Set<Perfil> getPerfils() {
		return perfils;
	}

	public void setPerfils(Set<Perfil> perfils) {
		this.perfils = perfils;
	}

	/**
	 * Construtor da Classe, Obrigando receber todos os parametros
	 * @param nome
	 * @param email
	 * @param dataNascimento
	 * @param situacao
	 */
	public Pessoa(@NotNull String nome, @NotNull String email, @NotNull LocalDate dataNascimento,@NotNull Boolean situacao) {
		super();
		this.nome = nome;
		this.email = email;
		this.dataNascimento = dataNascimento;
		this.situacao = situacao;
	}


	public Set<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(Set<Endereco> enderecos) {
		this.enderecos = enderecos;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}



	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getSituacao() {
		return situacao;
	}

	public void setSituacao(Boolean situacao) {
		this.situacao = situacao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pessoa other = (Pessoa) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Pessoa [id=" + id + ", nome=" + nome + ", email=" + email + ", dataNascimento=" + dataNascimento
				+ ", situacao=" + situacao + "]";
	}
	
	
	
	
	
	
	
	

}

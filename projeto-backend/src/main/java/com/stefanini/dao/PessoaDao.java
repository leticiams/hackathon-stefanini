package com.stefanini.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.TypedQuery;

import com.stefanini.dao.abstracao.GenericDao;
import com.stefanini.model.Pessoa;

public class PessoaDao extends GenericDao<Pessoa, Long> {

	public PessoaDao() {
		super(Pessoa.class);
	}

	public Optional<Pessoa> buscarPessoaPorEmail(String email){
		TypedQuery<Pessoa> q2 =
				entityManager.createQuery(" select p from Pessoa p where p.email=:email", Pessoa.class);
		q2.setParameter("email", email);
		return q2.getResultStream().findFirst();
	}
	
	public Optional<List<Pessoa>> buscarPessoaCheia(){
		TypedQuery<Pessoa> query = entityManager.createQuery(" SELECT DISTINCT p FROM Pessoa p  LEFT JOIN FETCH p.perfils perfil  LEFT JOIN FETCH p.enderecos endereco ORDER BY p.nome", Pessoa.class);
		return Optional.ofNullable(query.getResultList());
	}

	@Override
	public Optional<Pessoa> encontrar(Long id) {
		TypedQuery<Pessoa> q = entityManager.createQuery(" SELECT DISTINCT p FROM Pessoa p LEFT JOIN FETCH p.perfils perfil  LEFT JOIN FETCH p.enderecos endereco WHERE p.id = :id", Pessoa.class);
		q.setParameter("id", id);
		return q.getResultStream().findFirst();
	}

}

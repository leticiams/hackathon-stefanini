package com.stefanini.dao.abstracao;


import com.stefanini.dao.interfaces.IGenericDao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public abstract class GenericDao<T, I extends Serializable> implements IGenericDao<T, I> {


	@PersistenceContext(unitName="jpa")
	protected EntityManager entityManager;

	private Class<T> classe;

	protected GenericDao() {

	}

	protected GenericDao(Class<T> classe) {
		this();
		this.classe = classe;
	}

	public void daoFlush() {
		entityManager.flush();
	}

	public T salvar(@Valid T entity) {
		entityManager.persist(entity);
		return entity;
	}

	public T atualizar(@Valid T entity) {
		entityManager.merge(entity);
		return entity;
	}

	public void remover(I id) {
		T entity = encontrar(id).get();
		getEntityManager().remove(entity);
	}

	public Optional<List<T>> getList() {
		CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(classe);
		query.from(classe);
		return Optional.of(getEntityManager().createQuery(query).getResultList());
	}

	public Optional<T> encontrar(I id) {
		return Optional.ofNullable(getEntityManager().find(classe, id));
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

}

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

/**
 * @author joaopedromilhome
 *
 * @param <T>
 * @param <I>
 */
public abstract class GenericDao<T, I extends Serializable> implements IGenericDao<T, I> {


	@PersistenceContext(unitName="jpa")
	protected EntityManager entityManager;

	/**
	 * Classe que será efetuada as transacoes
	 */
	private Class<T> classe;

	protected GenericDao() {
	}

	protected GenericDao(Class<T> classe) {
		this();
		this.classe = classe;
	}

	/**
	 * @valid serve para validar a entidade antes de entrar no metodo, olhar o conceito de BEAN VALIDATION
	 * Sempre que for executar uma DML é necessario abrir uma transacao e fecha-la, pois senão a operacao não será comitada
	 */
	public T salvar(@Valid T entity) {
		entityManager.persist(entity);
		return entity;
	}
	/**
	 * @valid serve para validar a entidade antes de entrar no metodo, olhar o conceito de BEAN VALIDATION
	 * Sempre que for executar uma DML é necessario abrir uma transacao e fecha-la, pois senão a operacao não será comitada
	 */
	public T atualizar(@Valid T entity) {
		entityManager.merge(entity);
		return entity;
	}

	
	/**
	 * Sempre que for executar uma DML é necessario abrir uma transacao e fecha-la, pois senão a operacao não será comitada
	 */
	public void remover(I id) {
		T entity = encontrar(id).get();
		getEntityManager().remove(entity);
	}

	/**
	 * Não precisa de Transacao para efetuar DQL
	 */
	public Optional<List<T>> getList() {
		CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(classe);
		query.from(classe);
		return Optional.of(getEntityManager().createQuery(query).getResultList());
	}
	/**
	 * Não precisa de Transacao para efetuar DQL
	 */
	public Optional<T> encontrar(I id) {
		return Optional.ofNullable(getEntityManager().find(classe, id));
	}
	
	

	/**
	 * Obter o EntityManager
	 * @return
	 */
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	
	
}

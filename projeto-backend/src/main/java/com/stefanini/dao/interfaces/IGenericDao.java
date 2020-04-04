package com.stefanini.dao.interfaces;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface IGenericDao<T, I extends Serializable> {

	T salvar(@Valid T entity);

	T atualizar(@Valid T entity);

	void remover(@Valid I id);

	Optional<List<T>> getList();

	Optional<T> encontrar(I id);

}

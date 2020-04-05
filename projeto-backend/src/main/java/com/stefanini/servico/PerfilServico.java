package com.stefanini.servico;

import com.stefanini.dao.PerfilDao;
import com.stefanini.exception.NegocioException;
import com.stefanini.model.Perfil;

import javax.ejb.*;
import javax.inject.Inject;
import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class PerfilServico implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private PerfilDao dao;

	@Inject
	private PessoaPerfilServico pessoaPerfilServico;

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Perfil salvar(@Valid Perfil perfil) {
		return dao.salvar(perfil);
	}

	public Boolean validarPerfil(@Valid Perfil perfil){
		Optional<Perfil> perfil1 = dao.buscarPessoaPorNome(perfil.getNome());
		return perfil1.isEmpty();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Perfil atualizar(@Valid Perfil entity) {
		return dao.atualizar(entity);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void remover(@Valid Long id) throws NegocioException {
		if(pessoaPerfilServico.buscarPessoaPerfil(null,id).count() == 0){
			dao.remover(id);
			return;
		}
		throw new NegocioException("Não foi possível remover o perfil");
	}

	public Optional<List<Perfil>> getList() {
		return dao.getList();
	}

	public Optional<Perfil> encontrar(Long id) {
		return dao.encontrar(id);
	}

}

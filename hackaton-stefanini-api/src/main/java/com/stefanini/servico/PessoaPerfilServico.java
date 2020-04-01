package com.stefanini.servico;

import com.stefanini.dao.PerfilDao;
import com.stefanini.dao.PessoaPerfilDao;
import com.stefanini.model.Perfil;
import com.stefanini.model.PessoaPerfil;
import com.stefanini.resource.PessoaResource;

import javax.ejb.*;
import javax.inject.Inject;
import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * 
 * Classe de servico, as regras de negocio devem estar nessa classe
 * 
 * @author joaopedromilhome
 *
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class PessoaPerfilServico implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private PessoaPerfilDao dao;

	public Stream<PessoaPerfil> buscarPessoaPerfil(Long idPessoa, Long idPerfil) {
		return dao.buscarPessoaPerfil(idPessoa,idPerfil);
	}


	}

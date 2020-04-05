package com.stefanini.servico.teste;

import java.util.stream.Stream;

import javax.persistence.EntityManager;

import org.junit.Assert;
import org.junit.Test;

import com.stefanini.dao.PessoaPerfilDao;
import com.stefanini.model.PessoaPerfil;
import com.stefanini.servico.PessoaPerfilServico;

import mockit.Injectable;
import mockit.Mocked;
import mockit.Tested;

public class PessoaPerfilServicoTeste {
    @Injectable
    EntityManager em;

    @Tested
    PessoaPerfilServico pessoaPerfilServico;

    @Injectable
    @Mocked
    PessoaPerfilDao pessoaPerfilDao;

    @Test
    public void testePessoaPerfilServico() {
        Long idPessoa = 1L;
        Long idPerfil = 2L;

        Stream<PessoaPerfil> stream = pessoaPerfilServico.buscarPessoaPerfil(idPessoa, idPerfil);

        Assert.assertEquals(pessoaPerfilServico.buscarPessoaPerfil(idPessoa, idPerfil), stream);

    }

}
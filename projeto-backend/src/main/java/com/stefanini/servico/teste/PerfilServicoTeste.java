package com.stefanini.servico.teste;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.validation.Valid;

import com.stefanini.servico.PerfilServico;
import com.stefanini.servico.PessoaPerfilServico;
import org.junit.Before;
import org.junit.Test;

import com.stefanini.dao.PerfilDao;
import com.stefanini.dao.PessoaPerfilDao;
import com.stefanini.exception.NegocioException;
import com.stefanini.model.Endereco;
import com.stefanini.model.Perfil;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.Tested;
import mockit.Verifications;

public class PerfilServicoTeste {
    @Injectable
    EntityManager em;

    @Tested
    Perfil perfil;

    @Tested
    PerfilServico perfilServico;

    @Injectable
    PessoaPerfilServico pessoaPerfilServico;

    @Injectable
    @Mocked
    PerfilDao perfilDao;

    private Long id;
    private LocalDateTime dataHoraInclusao;
    private LocalDateTime dataHoraAlteracao;

    @Before
    public void setUp() {
        id = 1L;
        perfil = new Perfil();
        perfil.setId(id);
        perfil.setNome("nome");
        perfil.setDataHoraInclusao(dataHoraInclusao);
        perfil.setDataHoraAlteracao(dataHoraAlteracao);
    }

    @Test
    public void testeSalvarPerfil() {
        new Expectations() {
            {
                perfilServico.salvar((@Valid Perfil) any);
                result = perfil;
            }
        };

        Perfil retornoPerfil = perfilServico.salvar(perfil);
        assertEquals(retornoPerfil.getId(), perfil.getId());
    }

    @Test
    public void testeValidarPerfil() {
        new Expectations() {
            {
                perfilServico.validarPerfil(perfil);
                result = false;
            }
        };

        Boolean retorno = perfilServico.validarPerfil(perfil);
        assertFalse(retorno);
    }

    @Test
    public void testeAtualizarPerfil() {
        new Expectations() {
            {
                perfilServico.atualizar((@Valid Perfil) any);
                result = perfil;
            }
        };

        Perfil retornoPerfil = perfilServico.atualizar(perfil);
        assertEquals(retornoPerfil, perfil);
        assertEquals(retornoPerfil.getDataHoraAlteracao(), dataHoraAlteracao);

    }

    @Test
    public void testeRemoverPerfil() throws NegocioException {
        new Expectations() {
            {
                perfilServico.remover(id);
            }
        };

        pessoaPerfilServico.buscarPessoaPerfil(null, id);
        perfilDao.remover(id);

        new Verifications() {
            {
                perfilServico.remover(id);
                times = 1;
            }
        };
    }

    @Test
    public void testeGetListPerfil() {
        new Expectations() {
            {
                perfilServico.getList();
                result = Optional.of(perfil);
            }
        };

        Optional<List<Perfil>> getListPerfil = perfilServico.getList();

    }

    @Test
    public void testeEncontrarPerfil() {
        new Expectations() {
            {
                perfilServico.encontrar(0L);
                result = perfil;
            }
        };

        Optional<Perfil> encontrar = perfilServico.encontrar(id);
        assertEquals(encontrar.get(), perfil);

    }
}
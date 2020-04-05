package com.stefanini.servico.teste;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.validation.Valid;

import com.stefanini.servico.EnderecoServico;
import org.junit.Before;
import org.junit.Test;

import com.stefanini.dao.EnderecoDao;
import com.stefanini.exception.NegocioException;
import com.stefanini.model.Endereco;
import com.stefanini.model.Perfil;
import com.stefanini.model.Pessoa;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.Tested;
import mockit.Verifications;

public class EnderecoServicoTeste {
    @Injectable
    EntityManager entityManager;

    @Tested
    EnderecoServico enderecoServico;

    @Injectable
    @Mocked
    EnderecoDao enderecoDao;

    @Tested
    Endereco endereco;

    private Long id;

    @Before
    public void setUp() {
        id = 1L;
        endereco = new Endereco();
        endereco.setId(id);
    }

    @Test
    public void testeSalvarEndereco() {
        new Expectations() {
            {
                enderecoServico.salvar((@Valid Endereco) any);
                result = endereco;
            }
        };

        Endereco retornoEndereco = enderecoServico.salvar(endereco);
        assertEquals(retornoEndereco.getId(), endereco.getId());

    }

    @Test
    public void testeAtualizarEndereco() {
        new Expectations() {
            {
                enderecoServico.atualizar((@Valid Endereco) any);
                result = endereco;
            }
        };

        Endereco retornoEndereco = enderecoServico.atualizar(endereco);
        assertEquals(retornoEndereco.getId(), endereco.getId());
    }

    @Test
    public void testeRemoverEndereco() throws NegocioException {
        new Expectations() {
            {
                enderecoServico.remover(id);

            }
        };

        enderecoServico.remover(id);

        new Verifications() {
            {
                enderecoServico.remover(id);
                times = 1;
            }
        };
    }

    @Test
    public void testeEncontrarEndereco() {
        new Expectations() {
            {
                enderecoServico.encontrar(anyLong);
                result = Optional.of(endereco);
            }
        };

        Optional<Endereco> encontrar = enderecoServico.encontrar(1L);
        assertTrue(encontrar.isPresent());
        assertEquals(id, encontrar.get().getId());
    }

    @Test
    public void testeGetListEndereco() {
        new Expectations() {
            {
                enderecoServico.getList();
                result = Optional.of(endereco);
            }
        };

        Optional<List<Endereco>> getListEndereco = enderecoServico.getList();

    }

}
package com.stefanini.servico.teste;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.validation.Valid;

import com.stefanini.servico.EnderecoServico;
import com.stefanini.servico.PessoaPerfilServico;
import com.stefanini.servico.PessoaServico;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.stefanini.dao.PessoaDao;
import com.stefanini.exception.NegocioException;
import com.stefanini.model.Endereco;
import com.stefanini.model.Pessoa;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.Tested;
import mockit.Verifications;

public class PessoaServicoTeste {
    @Injectable
    EntityManager em;

    @Tested
    PessoaServico pessoaServico;

    @Injectable
    Endereco endereco;

    @Injectable
    @Mocked
    PessoaDao pessoaDao;

    @Injectable
    PessoaPerfilServico pessoaPerfilServico;

    @Injectable
    EnderecoServico enderecoServico;

    private Long id;
    private Pessoa pessoa;
    private Long idEnd;
    private List<Pessoa> pessoaLista;
    private Set<Endereco> enderecos = new HashSet<Endereco>();

    @Before
    public void setUp() {
        id = 1L;
        idEnd = 1L;
        pessoa = new Pessoa();
        pessoa.setId(id);
        pessoa.setNome("Simone");
        pessoaLista = Arrays.asList(pessoa);
        endereco.setId(idEnd);
        enderecos.add(endereco);
        pessoa.setEnderecos(enderecos);
    }

    @Test
    public void testePessoaSalvar() {
        new Expectations() {
            {
                pessoaDao.salvar((@Valid Pessoa) any);
                result = pessoa;
            }
        };

        Pessoa retornoPessoa = pessoaServico.salvar(pessoa);
        assertEquals(retornoPessoa.getId(), pessoa.getId());

    }

    @Test
    public void testeValidarPessoa() {
        new Expectations() {
            {
                pessoaDao.encontrar(id);
                result = pessoa;
            }
        };

        Boolean retornoVP = pessoaServico.pessoaValidaEmail(pessoa);
        assertTrue(retornoVP);
    }

    @Test
    public void testeAtualizarPessoa() {
        new Expectations() {
            {
                pessoaDao.atualizar((@Valid Pessoa) any);
                result = pessoa;
            }
        };

        Pessoa retornoPessoa = pessoaServico.atualizar(pessoa);
        assertEquals(retornoPessoa.getId(), pessoa.getId());
    }

    @Test
    public void testeRemoverPessoa() throws NegocioException {
        new Expectations() {
            {
                pessoaDao.remover(id);

            }
        };

        pessoaServico.remover(id);

        new Verifications() {
            {
                pessoaDao.remover(id);
                times = 1;
            }
        };
    }

    @Test
    public void testeEncontrarPessoa() {
        new Expectations() {
            {
                pessoaDao.encontrar(anyLong);
                result = Optional.of(pessoa);
            }
        };

        Optional<Pessoa> encontrar = pessoaServico.encontrar(1L);
        assertTrue(encontrar.isPresent());
        assertEquals(id, encontrar.get().getId());
        assertEquals("TEste", encontrar.get().getNome());
    }

    @Test
    public void testeObterPessoaComRelacionamentos() {
        new Expectations() {
            {
                pessoaDao.pessoasCheias();
                result = pessoaLista;
            }
        };

        Optional<List<Pessoa>> obterRetornoPessoaComRelacionamentos = pessoaServico.buscarPessoaCheia();
        assertFalse(obterRetornoPessoaComRelacionamentos.isEmpty());
        assertEquals(id, obterRetornoPessoaComRelacionamentos.get());
        assertEquals("Teste", obterRetornoPessoaComRelacionamentos.get());

        new Verifications() {
            {
                pessoaDao.pessoasCheias();
                times = 1;
            }
        }; // Saber se o método foi chamado pelo menos uma vez
    }

    @Test
    public void testeAtualizarCaminhoImagemPessoa() {
        // Mock das chamadas externas (pessoaDao.encontrar() e pessoaDao.atualizar())
        // feitas em pessoaServico.atualizarCaminhoImagemPessoa()
        String caminhoImagem = "caminhoImagem";
        Pessoa pessoaAtualizada = new Pessoa();

        pessoaAtualizada.setImagem(caminhoImagem);
        Optional<Pessoa> pessoaOpt = Optional.of(pessoa);

        new Expectations() {
            {
                pessoaDao.encontrar(id);
                result = pessoaOpt;
            }
        };
        new Expectations() {
            {
                pessoaDao.atualizar(pessoa);
                result = pessoaAtualizada;
            }
        };

    }

}

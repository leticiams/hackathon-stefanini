package com.stefanini.servico;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.validation.Valid;

import com.stefanini.dao.PessoaDao;
import com.stefanini.dao.PessoaPerfilDao;
import com.stefanini.exception.NegocioException;
import com.stefanini.model.Endereco;
import com.stefanini.model.Perfil;
import com.stefanini.model.Pessoa;
import com.stefanini.model.PessoaPerfil;

import static java.util.Objects.nonNull;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class PessoaServico implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private PessoaDao pessoaDao;

	@Inject
	private PessoaPerfilDao pessoaPerfilDao;

	@Inject
	private PessoaPerfilServico pessoaPerfilServico;

	@Inject
	private EnderecoServico enderecoServico;

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Pessoa salvar(@Valid Pessoa pessoa) {
		List<Endereco> enderecos = new ArrayList<>(pessoa.getEnderecos());
		List<Perfil> perfis = new ArrayList<>(pessoa.getPerfils());

		pessoa.getEnderecos().clear();
		pessoa.getPerfils().clear();

		if(nonNull(pessoa.getImagem()))
			pessoa.setImagem(decodeToImage(pessoa.getImagem()));

		Pessoa pessoaSalva = pessoaDao.salvar(pessoa);
		pessoaDao.daoFlush();

		perfis.forEach(perfil -> {
			pessoaPerfilDao.salvar(new PessoaPerfil(perfil, pessoaSalva));
		});

		for (Endereco enderecoSalvo : enderecos) {
			enderecoSalvo.setIdPessoa(pessoaSalva.getId());
			enderecoServico.salvar(enderecoSalvo);
		}

		return pessoaSalva;
	}

	public Boolean validarPessoa(@Valid Pessoa pessoa){
		Optional<Pessoa> pessoaValidaEmail = pessoaDao.buscarPessoaPorEmail(pessoa.getEmail());
		return pessoaValidaEmail.isPresent();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Pessoa atualizar(@Valid Pessoa pessoa) {
		pessoa.setImagem(decodeToImage(pessoa.getImagem())); ;
		return pessoaDao.atualizar(pessoa);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void remover(@Valid Long id) throws NegocioException {
		if(pessoaPerfilServico.buscarPessoaPerfil(id,null).count() == 0){
			pessoaDao.remover(id);
			return;
		}
		throw new NegocioException("Não foi possível remover a pessoa");
	}

	public Optional<List<Pessoa>> getList() {
		return pessoaDao.getList();
	}

	public Optional<List<Pessoa>> buscarPessoaCheia() {
		return pessoaDao.buscarPessoaCheia();
	}

	public String decodeToImage(String imagem ) {
		imagem = imagem.split(",")[1];

		String url = "C:\\temp\\desafio-final-stefanini\\projeto-backend\\src\\imagens";
		String url2 = "\\imagem"+ Math.random() + ".jpg";

		BufferedImage image = null;
		byte[] imageByte;
		try {
			imageByte = Base64.getDecoder().decode(imagem.getBytes());
			ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
			image = ImageIO.read(bis);
			bis.close();
			ImageIO.write((RenderedImage)image, "jpg", new File(url+url2));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return url + url2 ;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public FileInputStream urlImg (String localImg){

		String local = "C:\\temp\\desafio-final-stefanini\\projeto-backend\\src\\imagens"+localImg;

		FileInputStream file = null;

		try{
			file = new FileInputStream(local);
		}catch (FileNotFoundException e){
			e.printStackTrace();}

		return file;
	}


	public Optional<Pessoa> encontrar(Long id) {


		Optional<Pessoa> pessoa = pessoaDao.encontrar(id);

		if (pessoa.get().getImagem() != null){

			String urlPath = "http://localhost:8081/treinamento/api/pessoas/imagem/imagem0.";
			String local = pessoa.get().getImagem();

			String[] cocatenar = local.split(Pattern.quote("."));

			pessoa.get().setImagem(urlPath + cocatenar[1] + "." + cocatenar[2]);
		}
		return pessoa;
	}
}
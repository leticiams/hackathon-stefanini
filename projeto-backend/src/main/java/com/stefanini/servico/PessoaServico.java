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
import com.stefanini.exception.NegocioException;
import com.stefanini.model.Endereco;
import com.stefanini.model.Pessoa;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class PessoaServico implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private PessoaDao dao;

	@Inject
	private PessoaPerfilServico pessoaPerfilServico;

	@Inject
	private EnderecoServico enderecoServico;

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Pessoa salvar(@Valid Pessoa pessoa) {

		List<Endereco> enderecos = new ArrayList<>();

		for (Endereco enderecoDaPessoa : pessoa.getEnderecos()) {
			enderecos.add(enderecoDaPessoa);
		}

		pessoa.getEnderecos().clear();

		if(pessoa.getImagem() != null) {
			pessoa.setImagem(decodeToImage(pessoa.getImagem())); ;
		}

		Pessoa pessoaSalva = dao.salvar(pessoa);

		for (Endereco enderecoSalvo : enderecos) {
			enderecoSalvo.setIdPessoa(pessoaSalva.getId());
			enderecoServico.salvar(enderecoSalvo);
		}
		return pessoaSalva;
	}

	public Boolean validarPessoa(@Valid Pessoa pessoa){
		if(pessoa.getId() != null){
			Optional<Pessoa> encontrar = dao.encontrar(pessoa.getId());
			if(encontrar.get().getEmail().equals(pessoa.getEmail())){
				return Boolean.TRUE;
			}
		}
		Optional<Pessoa> pessoa1 = dao.buscarPessoaPorEmail(pessoa.getEmail());
		return pessoa1.isEmpty();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Pessoa atualizar(@Valid Pessoa pessoa) {
		pessoa.setImagem(decodeToImage(pessoa.getImagem())); ;
		return dao.atualizar(pessoa);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void remover(@Valid Long id) throws NegocioException {
		if(pessoaPerfilServico.buscarPessoaPerfil(id,null).count() == 0){
			dao.remover(id);
			return;
		}
		throw new NegocioException("Não foi possível remover a pessoa");
	}

	public Optional<List<Pessoa>> getList() {
		return dao.getList();
	}

	public Optional<List<Pessoa>> buscarPessoaCheia() {
		return dao.buscarPessoaCheia();
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


		Optional<Pessoa> pessoa = dao.encontrar(id);

		if (pessoa.get().getImagem() != null){

			String urlPath = "http://localhost:8081/treinamento/api/pessoas/imagem/imagem0.";
			String local = pessoa.get().getImagem();

			String[] cocatenar = local.split(Pattern.quote("."));

			pessoa.get().setImagem(urlPath + cocatenar[1] + "." + cocatenar[2]);
		}
		return pessoa;
	}


}
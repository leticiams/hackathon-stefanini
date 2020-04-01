package com.stefanini.dto;

import java.io.Serializable;

public class SucessoDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String mensagem;

	public SucessoDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SucessoDto(String mensagem) {
		this.mensagem = mensagem;
	}
}

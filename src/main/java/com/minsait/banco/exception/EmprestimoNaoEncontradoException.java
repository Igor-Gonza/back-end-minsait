package com.minsait.banco.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EmprestimoNaoEncontradoException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public EmprestimoNaoEncontradoException(Long id, String cpf) {
		super(String.format("Empréstimo com ID %s não encontrado no CPF %s", id, cpf));
	}
	
}

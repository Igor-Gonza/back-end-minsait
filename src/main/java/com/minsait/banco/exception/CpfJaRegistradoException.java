package com.minsait.banco.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CpfJaRegistradoException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public CpfJaRegistradoException(String cpf) {
		super(String.format("O CPF %s já está registrado no sistema", cpf));
	}
	
}

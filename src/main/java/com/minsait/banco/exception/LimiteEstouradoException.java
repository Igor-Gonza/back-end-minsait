package com.minsait.banco.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class LimiteEstouradoException extends Exception {

	private static final long serialVersionUID = 1L;

	public LimiteEstouradoException(String cpf) {
		super(String.format("A transação para o CPF %s não foi concluída, devido à saldo insuficiente", cpf));
	}
}

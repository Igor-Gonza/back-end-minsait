package com.minsait.banco.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.minsait.banco.dto.EmprestimoDtoRequest;
import com.minsait.banco.entity.Emprestimo;
import com.minsait.banco.entity.Relacionamento;
import com.minsait.banco.exception.ClienteNaoEncontradoException;
import com.minsait.banco.exception.EmprestimoNaoEncontradoException;
import com.minsait.banco.exception.LimiteEstouradoException;
import com.minsait.banco.service.EmprestimoService;

import jakarta.validation.Valid;

@RestController
public class EmprestimoController {

	private EmprestimoService emprestimoService;

	@Autowired
	public EmprestimoController(EmprestimoService emprestimoService) {
		this.emprestimoService = emprestimoService;
	}
	
	@GetMapping("/emprestimos/relacionamentos")
	public Relacionamento[] retornaRelacionamentosDeEmprestimo() {
		return Relacionamento.values();
	}

	@PostMapping("/clientes/{cpf}/emprestimos")
	@ResponseStatus(HttpStatus.CREATED)
	public Emprestimo cadastrarEmprestimo(@PathVariable String cpf, @Valid @RequestBody EmprestimoDtoRequest emprestimoDtoRequest)
	throws ClienteNaoEncontradoException, LimiteEstouradoException {
		Emprestimo emprestimoCriado = EmprestimoDtoRequest.criarEmprestimo(emprestimoDtoRequest);
		emprestimoCriado = this.emprestimoService.cadastraEmprestimo(cpf, emprestimoCriado);
		return emprestimoCriado;
	}

	@GetMapping("/clientes/{cpf}/emprestimos")
	public List<Emprestimo> retornaTodosEmprestimosPorCpf(@PathVariable String cpf)
	throws ClienteNaoEncontradoException {
		return this.emprestimoService.retornaEmprestimosPorCpf(cpf);
	}

	@GetMapping("/clientes/{cpf}/emprestimos/{id}")
	public Emprestimo retornaUmEmprestimoPorCpf(@PathVariable String cpf, @PathVariable Long id)
	throws ClienteNaoEncontradoException, EmprestimoNaoEncontradoException {
		Emprestimo emprestimoRetornado = this.emprestimoService.retornaUmEmprestimo(cpf, id);
		return emprestimoRetornado;
	}

	@DeleteMapping("/clientes/{cpf}/emprestimos/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletarEmprestimo(@PathVariable String cpf, @PathVariable Long id)
	throws ClienteNaoEncontradoException, EmprestimoNaoEncontradoException {
		this.emprestimoService.deletaEmprestimo(cpf, id);
	}

}

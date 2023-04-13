package com.minsait.banco.controller;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.minsait.banco.dto.ClienteDto;
import com.minsait.banco.entity.Cliente;
import com.minsait.banco.exception.ClienteNaoEncontradoException;
import com.minsait.banco.exception.CpfJaRegistradoException;
import com.minsait.banco.service.ClienteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

	private ClienteService clienteService;

	@Autowired
	public ClienteController(ClienteService clienteService) {
		this.clienteService = clienteService;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ClienteDto cadastrarCliente(@Valid @RequestBody ClienteDto clienteDto) throws CpfJaRegistradoException {
		Cliente clienteCriado = ClienteDto.criarCliente(clienteDto);
		clienteCriado = this.clienteService.cadastraCliente(clienteCriado);
		return ClienteDto.criarDto(clienteCriado);
	}

	@GetMapping
	public List<ClienteDto> retornarTodosClientes() {
		List<ClienteDto> clientes = new LinkedList<>();
		this.clienteService.retornaTodosClientes().forEach(cliente -> {
			clientes.add(ClienteDto.criarDto(cliente));
		});
		return clientes;
	}

	@GetMapping("/{cpf}")
	public ClienteDto retornarClientePorCpf(@PathVariable String cpf) throws ClienteNaoEncontradoException {
		Cliente clienteRetornado = this.clienteService.retornaUmCliente(cpf);
		return ClienteDto.criarDto(clienteRetornado);
	}

	@PutMapping("/{cpf}")
	public ClienteDto atualizarCliente(@PathVariable String cpf, @Valid @RequestBody ClienteDto clienteDto)
	throws ClienteNaoEncontradoException {
		Cliente clienteAtualizado = ClienteDto.criarCliente(clienteDto);
		clienteAtualizado = this.clienteService.atualizaCliente(cpf, clienteAtualizado);
		return ClienteDto.criarDto(clienteAtualizado);
	}

	@DeleteMapping("/{cpf}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletarCliente(@PathVariable String cpf) throws ClienteNaoEncontradoException {
		this.clienteService.deletaCliente(cpf);
	}

}

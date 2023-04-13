package com.minsait.banco.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.minsait.banco.entity.Cliente;
import com.minsait.banco.exception.ClienteNaoEncontradoException;
import com.minsait.banco.exception.CpfJaRegistradoException;
import com.minsait.banco.repository.ClienteRepository;

@Service
public class ClienteService {
	
	private ClienteRepository clienteRepository;
	
	@Autowired
	public ClienteService(ClienteRepository clienteRepository) {
		this.clienteRepository = clienteRepository;
	}
	
	public Cliente cadastraCliente(Cliente cliente) throws CpfJaRegistradoException {
		
		String cpf = cliente.getCpf();
		
		if(this.clienteRepository.existsByCpf(cpf))
			throw new CpfJaRegistradoException(cpf);
		
		return this.clienteRepository.save(cliente);
	}

	public List<Cliente> retornaTodosClientes() {
		return this.clienteRepository.findAll();
	}
	
	public Cliente retornaUmCliente(String cpf) throws ClienteNaoEncontradoException {
		
		if(!this.clienteRepository.existsByCpf(cpf))
			throw new ClienteNaoEncontradoException(cpf);
		
		return this.clienteRepository.findByCpf(cpf).get();
	}
	
	public Cliente atualizaCliente(String cpf, Cliente cliente) throws ClienteNaoEncontradoException {
		
		if(!this.clienteRepository.existsByCpf(cpf))
			throw new ClienteNaoEncontradoException(cpf);
		
		cliente.setCpf(cpf);
		return this.clienteRepository.save(cliente);
	}
	
	public void deletaCliente(String cpf) throws ClienteNaoEncontradoException {
		
		if(!this.clienteRepository.existsByCpf(cpf))
			throw new ClienteNaoEncontradoException(cpf);
		
		this.clienteRepository.deleteById(cpf);
	}
}

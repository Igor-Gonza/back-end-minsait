package com.minsait.banco.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.minsait.banco.entity.Cliente;
import com.minsait.banco.entity.Emprestimo;
import com.minsait.banco.exception.ClienteNaoEncontradoException;
import com.minsait.banco.exception.EmprestimoNaoEncontradoException;
import com.minsait.banco.exception.LimiteEstouradoException;
import com.minsait.banco.repository.ClienteRepository;
import com.minsait.banco.repository.EmprestimoRepository;

@Service
public class EmprestimoService {

	private EmprestimoRepository emprestimoRepository;
	private ClienteRepository clienteRepository;

	@Autowired
	public EmprestimoService(EmprestimoRepository emprestimoRepository, ClienteRepository clienteRepository) {
		this.emprestimoRepository = emprestimoRepository;
		this.clienteRepository = clienteRepository;
	}

	public Emprestimo cadastraEmprestimo(String cpf, Emprestimo emprestimo)
	throws ClienteNaoEncontradoException, LimiteEstouradoException {
		
		if(!this.clienteRepository.existsByCpf(cpf))
			throw new ClienteNaoEncontradoException(cpf);
		
		Cliente cliente = this.clienteRepository.findByCpf(cpf).get();
		List<Emprestimo> listaEmprestimos = this.emprestimoRepository.findAllById(cliente.getIdEmprestimos());
		
		BigDecimal debitoTotal = BigDecimal.ZERO;
		
		for(Emprestimo e: listaEmprestimos)
			debitoTotal = debitoTotal.add(e.getValorInicial());
		
		debitoTotal = debitoTotal.add(emprestimo.getValorInicial());
		
		BigDecimal limiteCliente = cliente.getRendimentoMensal().multiply(new BigDecimal("10"));
		
		if(debitoTotal.compareTo(limiteCliente) > 0)
			throw new LimiteEstouradoException(cpf);
		
		emprestimo.setCpfCliente(cpf);
		emprestimo.calcularValorFinal(this.clienteRepository.findByCpf(cpf).get());
		
		Emprestimo emprestimoCadastrado = this.emprestimoRepository.save(emprestimo);
		
		cliente.getIdEmprestimos().add(emprestimoCadastrado.getId());
		this.clienteRepository.save(cliente);
		
		return emprestimoCadastrado;
	}

	public List<Emprestimo> retornaEmprestimosPorCpf(String cpf)
	throws ClienteNaoEncontradoException {

		if(!this.clienteRepository.existsByCpf(cpf))
			throw new ClienteNaoEncontradoException(cpf);
		
		Cliente cliente = this.clienteRepository.findByCpf(cpf).get();
		
		return this.emprestimoRepository.findAllById(cliente.getIdEmprestimos());
	}

	public Emprestimo retornaUmEmprestimo(String cpf, Long id)
	throws ClienteNaoEncontradoException, EmprestimoNaoEncontradoException {
		
		if(!this.clienteRepository.existsByCpf(cpf))
			throw new ClienteNaoEncontradoException(cpf);
		
		if(!this.emprestimoRepository.existsById(id))
			throw new EmprestimoNaoEncontradoException(id, cpf);
		
		Emprestimo emprestimo = this.emprestimoRepository.findById(id).get();
		
		if(!emprestimo.getCpfCliente().equals(cpf))
			throw new EmprestimoNaoEncontradoException(id, cpf);
		
		return emprestimo;
	}

	public void deletaEmprestimo(String cpf, Long id)
	throws ClienteNaoEncontradoException, EmprestimoNaoEncontradoException {
		
		if(!this.clienteRepository.existsByCpf(cpf))
			throw new ClienteNaoEncontradoException(cpf);
		
		if(!this.emprestimoRepository.existsById(id))
			throw new EmprestimoNaoEncontradoException(id, cpf);
		
		Emprestimo emprestimo = this.emprestimoRepository.findById(id).get();
		
		if(emprestimo == null || !emprestimo.getCpfCliente().equals(cpf))
			throw new EmprestimoNaoEncontradoException(id, cpf);
		
		Cliente cliente = this.clienteRepository.findByCpf(cpf).get();
		cliente.getIdEmprestimos().remove(emprestimo.getId());
		this.clienteRepository.save(cliente);
		
		this.emprestimoRepository.deleteById(id);
	}

}

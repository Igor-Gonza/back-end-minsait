package com.minsait.banco.dto;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.br.CPF;

import com.minsait.banco.entity.Cliente;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ClienteDto {

	@Id
	@CPF(message = "CPF inválido")
	private String cpf;
	
	@NotBlank(message = "Campo 'nome' é obrigatório")
	private String nome;

	@Pattern(regexp = "\\d{10}\\d?", message = "Telefone inválido")
	@NotBlank(message = "Campo 'telefone' é obrigatório")
	private String telefone;
	
	@Pattern(regexp = "\\d{8}", message = "CEP inválido")
	@NotBlank(message = "Campo 'cep' é obrigatório")
	private String cep;
	
	@NotBlank(message = "Campo 'ruaEndereco' é obrigatório")
	private String ruaEndereco;
	
	@NotBlank(message = "Campo 'numeroEndereco' é obrigatório")
	private String numeroEndereco;
	
	@NotNull(message = "Campo 'rendimentoMensal' é obrigatório")
	private BigDecimal rendimentoMensal;

	public ClienteDto(String cpf, String nome, String telefone, String cep,
			String ruaEndereco, String numeroEndereco, BigDecimal rendimentoMensal) {
		this.cpf = cpf;
		this.nome = nome;
		this.telefone = telefone;
		this.cep = cep;
		this.ruaEndereco = ruaEndereco;
		this.numeroEndereco = numeroEndereco;
		this.rendimentoMensal = rendimentoMensal;
	}
	
	public static ClienteDto criarDto(Cliente c) {
		ClienteDto dto = new ClienteDto(c.getCpf(), c.getNome(), c.getTelefone(), c.getCep(), 
				c.getRuaEndereco(), c.getNumeroEndereco(), c.getRendimentoMensal());
		return dto;
	}
	
	public static Cliente criarCliente(ClienteDto dto) {
		Cliente cliente = new Cliente(dto.getCpf(), dto.getNome(), dto.getTelefone(), dto.getCep(), 
				dto.getRuaEndereco(), dto.getNumeroEndereco(), dto.getRendimentoMensal());
		return cliente;
	}
}

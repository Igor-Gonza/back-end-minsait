package com.minsait.banco.entity;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.validator.constraints.br.CPF;

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
public class Cliente {
	
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
	
	private List<Long> idEmprestimos;

	public Cliente(String cpf, String nome, String telefone, String cep,
			String ruaEndereco, String numeroEndereco, BigDecimal rendimentoMensal) {
		this.cpf = cpf;
		this.nome = nome;
		this.telefone = telefone;
		this.cep = cep;
		this.ruaEndereco = ruaEndereco;
		this.numeroEndereco = numeroEndereco;
		this.rendimentoMensal = rendimentoMensal;
		this.idEmprestimos = new LinkedList<>();
	}
	
}

package com.minsait.banco.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Emprestimo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@CPF(message = "CPF inválido")
	private String cpfCliente;

	@NotNull(message = "Campo 'valorInicial' é obrigatório")
	@Positive(message = "Campo 'valorInicial' deve ter valor positivo")
	private BigDecimal valorInicial;

	private BigDecimal valorFinal;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull(message = "Campo 'dataInicial' é obrigatório")
	private LocalDate dataInicial;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull(message = "Campo 'dataFinal' é obrigatório")
	private LocalDate dataFinal;
	
	@NotNull
	private Relacionamento relacionamento;
	
	public Emprestimo(String cpfCliente, BigDecimal valorInicial, LocalDate dataInicial,
			LocalDate dataFinal, Relacionamento relacionamento) {
		this.cpfCliente = cpfCliente;
		this.valorInicial = valorInicial;
		this.dataInicial = dataInicial;
		this.dataFinal = dataFinal;
		this.relacionamento = relacionamento;
	}

	public void calcularValorFinal(Cliente cliente) {
		setValorFinal(this.relacionamento.calcularValorFinal(valorInicial, cliente));
	}
	
}

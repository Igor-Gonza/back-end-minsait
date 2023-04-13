package com.minsait.banco.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

import com.minsait.banco.entity.Emprestimo;
import com.minsait.banco.entity.Relacionamento;

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
public class EmprestimoDtoRequest {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@CPF(message = "CPF inválido")
	private String cpfCliente;

	@NotNull(message = "Campo 'valorInicial' é obrigatório")
	@Positive(message = "Campo 'valorInicial' deve ter valor positivo")
	private BigDecimal valorInicial;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull(message = "Campo 'dataInicial' é obrigatório")
	private LocalDate dataInicial;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull(message = "Campo 'dataFinal' é obrigatório")
	private LocalDate dataFinal;
	
	@NotNull
	private Relacionamento relacionamento;

	public EmprestimoDtoRequest(BigDecimal valorInicial, LocalDate dataInicial,
			LocalDate dataFinal, Relacionamento relacionamento) {
		this.valorInicial = valorInicial;
		this.dataInicial = dataInicial;
		this.dataFinal = dataFinal;
		this.relacionamento = relacionamento;
	}
	
	public static EmprestimoDtoRequest criarDto(Emprestimo emprestimo) {
		EmprestimoDtoRequest dto = new EmprestimoDtoRequest();
		dto.setValorInicial(emprestimo.getValorInicial());
		dto.setDataInicial(emprestimo.getDataInicial());
		dto.setDataFinal(emprestimo.getDataFinal());
		dto.setRelacionamento(emprestimo.getRelacionamento());
		return dto;
	}
	
	public static Emprestimo criarEmprestimo(EmprestimoDtoRequest dto) {
		Emprestimo emprestimo = new Emprestimo();
		emprestimo.setValorInicial(dto.getValorInicial());
		emprestimo.setDataInicial(dto.getDataInicial());
		emprestimo.setDataFinal(dto.getDataFinal());
		emprestimo.setRelacionamento(dto.getRelacionamento());
		return emprestimo;
	}
}

package com.minsait.banco.entity;

import java.math.BigDecimal;

public enum Relacionamento {
	BRONZE {
		@Override
		public BigDecimal calcularValorFinal(BigDecimal valorInicial, Cliente cliente) {
			return valorInicial.multiply(new BigDecimal("1.8"));
		}
	},
	PRATA {
		@Override
		public BigDecimal calcularValorFinal(BigDecimal valorInicial, Cliente cliente) {
			return valorInicial.multiply(new BigDecimal(valorInicial.doubleValue() > 5000 ? "1.4" : "1.6"));
		}
	},
	OURO {
		@Override
		public BigDecimal calcularValorFinal(BigDecimal valorInicial, Cliente cliente) {
			return valorInicial.multiply(new BigDecimal(cliente.getIdEmprestimos().size() <= 1 ? "1.2" : "1.3"));
		}
	};

	public abstract BigDecimal calcularValorFinal(BigDecimal valorInicial, Cliente cliente);
}

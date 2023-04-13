package com.minsait.banco.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.minsait.banco.entity.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, String> {

	public boolean existsByCpf(String cpf);
	public Optional<Cliente> findByCpf(String cpf);
	
}

package com.eventos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.eventos.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

	List<Cliente> findByEstado(String estado);
	
}

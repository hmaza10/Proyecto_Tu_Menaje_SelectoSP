package com.eventos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.eventos.entity.Contrato;

import jakarta.transaction.Transactional;

public interface ContratoRepository extends JpaRepository<Contrato, Integer>{
	@Modifying
    @Transactional
	void deleteByEvento_IdEvento(int idEvento);
	Contrato findByEvento_IdEvento(int idEvento);
}

package com.eventos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.eventos.entity.EventoServicio;

import jakarta.transaction.Transactional;
import java.util.List;

public interface EventoServicioRepository extends JpaRepository<EventoServicio, Integer>{
	@Modifying
    @Transactional
	void deleteByEvento_IdEvento(int idEvento);
	
	List<EventoServicio> findByEvento_IdEvento(int idEvento);
}

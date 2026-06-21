package com.eventos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eventos.entity.Servicio;
import java.util.List;

public interface ServicioRepository extends JpaRepository<Servicio, Integer>{

	List<Servicio> findByEstado(String estado);
	
}

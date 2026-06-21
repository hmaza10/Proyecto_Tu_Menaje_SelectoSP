package com.eventos.service;

import java.util.List;

import com.eventos.entity.Servicio;

public interface ServicioService {

	List<Servicio> listar();

	Servicio buscarPorId(Integer id);

	Servicio guardar(Servicio servicio);

	void cambiarEstado(Integer id);
	
	List<Servicio> listarActivos();
	
}

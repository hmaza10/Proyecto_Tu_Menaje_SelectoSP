package com.eventos.service;

import java.util.List;

import com.eventos.entity.Evento;

public interface EventoService {

	List<Evento> listar();

	Evento buscarPorId(Integer id);

	Evento guardar(Evento evento);

	void eliminar(int id);
	
	void guardarEventoConServicios(Evento evento,List<Integer> serviciosIds);
	
	List<Evento> listarPorFecha();
	
	List<Object[]> contarEventosPorTipo();
	
	long contarPorEstado(String estado);
	
	List<Evento> listarConfirmados();
	
	

}

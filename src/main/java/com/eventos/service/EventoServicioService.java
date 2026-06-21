package com.eventos.service;

import com.eventos.entity.EventoServicio;
import java.util.List;

public interface EventoServicioService {
	
	List<EventoServicio> listar();

    EventoServicio buscarPorId(int id);

    EventoServicio guardar(EventoServicio eventoServicio);

    void eliminar(Integer id);
}

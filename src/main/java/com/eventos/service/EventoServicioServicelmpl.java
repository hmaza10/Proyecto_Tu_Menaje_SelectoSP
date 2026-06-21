package com.eventos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eventos.entity.EventoServicio;
import com.eventos.repository.EventoServicioRepository;

@Service
public class EventoServicioServicelmpl implements EventoServicioService{

	@Autowired
	private EventoServicioRepository repo;
	
	@Override
	public List<EventoServicio> listar() {
		return repo.findAll();
	}

	@Override
	public EventoServicio buscarPorId(int id) {
		return repo.findById(id).orElse(null);
	}

	@Override
	public EventoServicio guardar(EventoServicio eventoServicio) {
		return repo.save(eventoServicio);
	}

	@Override
	public void eliminar(Integer id) {
		 repo.deleteById(id);
		
	}

}

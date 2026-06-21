package com.eventos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eventos.entity.Servicio;
import com.eventos.repository.ServicioRepository;

@Service
public class ServicioServicelmpl implements ServicioService{

	@Autowired
	private ServicioRepository repoServicio;
	
	@Override
	public List<Servicio> listar() {
		return repoServicio.findAll();
	}

	@Override
	public Servicio buscarPorId(Integer id) {
		return repoServicio.findById(id).orElse(null);
	}

	@Override
	public Servicio guardar(Servicio servicio) {
		return repoServicio.save(servicio);
	}

	@Override
	public void cambiarEstado(Integer id) {
		Servicio servicio =
	            repoServicio.findById(id)
	            .orElse(null);

	    if(servicio != null) {

	        if("Activo".equals(servicio.getEstado())) {

	            servicio.setEstado("Inactivo");

	        } else {

	            servicio.setEstado("Activo");
	        }

	        repoServicio.save(servicio);
	    }
		
	}

	@Override
	public List<Servicio> listarActivos() {
		 return repoServicio.findByEstado("Activo");
	}


}

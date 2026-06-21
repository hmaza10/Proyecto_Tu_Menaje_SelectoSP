package com.eventos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eventos.entity.Rol;
import com.eventos.repository.RolRepository;

@Service
public class RolServicelmpl implements RolService{

	@Autowired
	private RolRepository repoRol;

	@Override
	public List<Rol> listar() {
		return repoRol.findAll();
	}

	@Override
	public Rol buscarPorId(Integer id) {
		return repoRol.findById(id).orElse(null);
	}
}

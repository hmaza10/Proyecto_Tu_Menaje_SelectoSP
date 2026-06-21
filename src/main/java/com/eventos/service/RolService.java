package com.eventos.service;

import com.eventos.entity.Rol;
import java.util.List;

public interface RolService {

	List<Rol> listar();

	Rol buscarPorId(Integer id);
	
}

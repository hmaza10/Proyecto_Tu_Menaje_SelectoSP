package com.eventos.service;

import java.util.List;

import com.eventos.entity.Usuario;

public interface UsuarioService {
	
	List<Usuario> listar();

	Usuario buscarPorId(Integer id);

	Usuario guardar(Usuario usuario);

	void cambiarEstado(Integer id);
}

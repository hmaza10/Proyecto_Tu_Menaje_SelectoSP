package com.eventos.service;

import java.util.List;

import com.eventos.entity.Proveedor;

public interface ProveedorService {

	List<Proveedor> listar();

	Proveedor buscarPorId(Integer id);

	Proveedor guardar(Proveedor proveedor);

	void cambiarEstado(Integer id);
	
	List<Proveedor> listarActivos();
	
}

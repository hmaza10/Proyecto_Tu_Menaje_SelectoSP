package com.eventos.service;

import java.util.List;

import com.eventos.entity.Contrato;

public interface ContratoService {

	List<Contrato> listar();

	Contrato buscarPorId(Integer id);

	Contrato guardar(Contrato contrato);

	void eliminar(Integer id);
	
}

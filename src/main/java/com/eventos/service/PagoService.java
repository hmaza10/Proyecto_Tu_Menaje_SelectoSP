package com.eventos.service;

import java.util.List;

import com.eventos.entity.Pago;

public interface PagoService {

	List<Pago> listar();

	Pago buscarPorId(Integer id);

	Pago guardar(Pago pago);

	void eliminar(Integer id);
	
	double obtenerTotalPagadoPorEvento(int idEvento);

}

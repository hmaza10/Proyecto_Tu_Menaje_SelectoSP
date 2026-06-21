package com.eventos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eventos.entity.Pago;
import com.eventos.repository.PagoRepository;

@Service
public class PagoServicelmpl implements PagoService{

	@Autowired
	private PagoRepository repoPago;

	@Override
	public List<Pago> listar() {
		return repoPago.findAllByOrderByIdPagoDesc();
	}

	@Override
	public Pago buscarPorId(Integer id) {
		return repoPago.findById(id).orElse(null);
	}

	@Override
	public Pago guardar(Pago pago) {
		return repoPago.save(pago);
	}

	@Override
	public void eliminar(Integer id) {
		repoPago.deleteById(id);
		
	}

	@Override
	public double obtenerTotalPagadoPorEvento(int idEvento) {
		 Double total =
			        repoPago.obtenerTotalPagadoPorEvento(idEvento);

			    return total != null ? total : 0.0;
	}

	
}

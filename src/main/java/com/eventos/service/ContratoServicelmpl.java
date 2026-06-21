package com.eventos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eventos.entity.Contrato;
import com.eventos.repository.ContratoRepository;

@Service
public class ContratoServicelmpl implements ContratoService{
	
	@Autowired
	private ContratoRepository repoContrato;

	@Override
	public List<Contrato> listar() {
		return repoContrato.findAll();
	}

	@Override
	public Contrato buscarPorId(Integer id) {
		return repoContrato.findById(id).orElse(null);
	}

	@Override
	public Contrato guardar(Contrato contrato) {
		return repoContrato.save(contrato);
	}

	@Override
	public void eliminar(Integer id) {
		repoContrato.deleteById(id);
		
	}
	

}

package com.eventos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eventos.entity.Cliente;
import com.eventos.repository.ClienteRepository;


@Service
public class ClienteServiceImpl implements ClienteService{

	
	@Autowired
	private ClienteRepository repoCliente;
	
	@Override
	public List<Cliente> listar() {
		return repoCliente.findAll() ;
	}

	@Override
	public Cliente buscarPorId(int id) {
		return repoCliente.findById(id).orElse(null);
	}

	@Override
	public Cliente guardar(Cliente cliente) {
		return repoCliente.save(cliente);
	}

	@Override
	public void eliminar(int id) {
		repoCliente.deleteById(id);
	}

	@Override
	public List<Cliente> listarActivos() {
		return repoCliente.findByEstado("Activo");
	}

}

package com.eventos.service;

import java.util.List;
import com.eventos.entity.Cliente;

public interface ClienteService {
	
	List<Cliente> listar();

    Cliente buscarPorId(int id);

    Cliente guardar(Cliente cliente);

    void eliminar(int id);
    
    List<Cliente> listarActivos();
	
}

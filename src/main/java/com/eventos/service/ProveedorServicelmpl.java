package com.eventos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eventos.entity.Proveedor;
import com.eventos.repository.ProveedorRepository;

@Service
public class ProveedorServicelmpl implements ProveedorService{

	@Autowired
	private ProveedorRepository repoProveedor;

	@Override
	public List<Proveedor> listar() {
		return repoProveedor.findAll();
	}

	@Override
	public Proveedor buscarPorId(Integer id) {
		return repoProveedor.findById(id).orElse(null);
	}

	@Override
	public Proveedor guardar(Proveedor proveedor) {
		return repoProveedor.save(proveedor);
	}

	@Override
	public void cambiarEstado(Integer id) {
		 Proveedor proveedor =
		            repoProveedor.findById(id)
		            .orElse(null);

		    if(proveedor != null) {

		        if("Activo".equals(proveedor.getEstado())) {

		            proveedor.setEstado("Inactivo");

		        } else {

		            proveedor.setEstado("Activo");
		        }

		        repoProveedor.save(proveedor);
		    }
		
	}

	@Override
	public List<Proveedor> listarActivos() {
		return repoProveedor.findByEstado("Activo");
	}

}

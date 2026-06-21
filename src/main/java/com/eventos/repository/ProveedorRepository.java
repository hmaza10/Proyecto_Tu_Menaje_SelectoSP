package com.eventos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import com.eventos.entity.Proveedor;

public interface ProveedorRepository extends JpaRepository<Proveedor, Integer>{

	List<Proveedor> findByEstado(String estado);
}

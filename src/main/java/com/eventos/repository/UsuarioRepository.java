package com.eventos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eventos.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

}

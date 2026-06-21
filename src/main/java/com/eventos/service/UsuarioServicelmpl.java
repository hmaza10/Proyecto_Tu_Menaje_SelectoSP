package com.eventos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eventos.entity.Usuario;
import com.eventos.repository.UsuarioRepository;

@Service
public class UsuarioServicelmpl implements UsuarioService{
	
	@Autowired
	private UsuarioRepository repoUsuario;

	@Override
	public List<Usuario> listar() {
		return repoUsuario.findAll();
	}

	@Override
	public Usuario buscarPorId(Integer id) {
		return repoUsuario.findById(id).orElse(null);
	}

	@Override
	public Usuario guardar(Usuario usuario) {
		return repoUsuario.save(usuario);
	}

	@Override
	public void cambiarEstado(Integer id) {
		Usuario usuario =
	            repoUsuario.findById(id)
	            .orElse(null);

	    if(usuario != null) {

	        if("Activo".equals(usuario.getEstado())) {

	            usuario.setEstado("Inactivo");

	        } else {

	            usuario.setEstado("Activo");
	        }

	        repoUsuario.save(usuario);
	    }
		
	}
	
	

}

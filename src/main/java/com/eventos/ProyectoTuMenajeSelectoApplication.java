package com.eventos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import com.eventos.repository.ClienteRepository;

@SpringBootApplication
public class ProyectoTuMenajeSelectoApplication implements CommandLineRunner{
	
	@Autowired
	private ClienteRepository repoCliente;

	public static void main(String[] args) {
		SpringApplication.run(ProyectoTuMenajeSelectoApplication.class, args);
	}
	
	@Override
	public void run(String ...args)throws Exception{
		System.out.println("=============================");
		System.out.println("LISTADO DE CLIENTES");
		System.out.println("=============================");
		repoCliente.findAll().forEach(cliente->{
			System.out.println(
					cliente.getIdCliente()+"-"+
			        cliente.getNombre()+"-"+
					cliente.getApellido()+"-"+
					cliente.getTelefono()+"-"+
					cliente.getDireccion()+"-"+
					cliente.getCorreo()
			        );		
		});
	}
	

}

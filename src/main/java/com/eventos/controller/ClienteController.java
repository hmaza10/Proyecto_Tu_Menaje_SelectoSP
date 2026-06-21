package com.eventos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.eventos.entity.Cliente;
import com.eventos.service.ClienteService;

@Controller
@RequestMapping("/gestioncliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/lista")
    public String listar(Model model) {
        model.addAttribute("clientes", clienteService.listar());
        return "cliente/listaClientes";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "cliente/registrarCliente";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Cliente cliente) {

        if(cliente.getEstado() == null ||
           cliente.getEstado().isEmpty()) {

            cliente.setEstado("Activo");
        }

        clienteService.guardar(cliente);

        return "redirect:/gestioncliente/lista";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable int id, Model model) {
        model.addAttribute("cliente", clienteService.buscarPorId(id));
        return "cliente/registrarCliente";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable int id) {
        clienteService.eliminar(id);
        return "redirect:/gestioncliente/lista";
    }
    
    @GetMapping("/cambiarEstado/{id}")
    public String cambiarEstado(@PathVariable Integer id) {

        Cliente cliente = clienteService.buscarPorId(id);

        if(cliente.getEstado().equals("Activo")) {

            cliente.setEstado("Inactivo");

        } else {

            cliente.setEstado("Activo");
        }

        clienteService.guardar(cliente);

        return "redirect:/gestioncliente/lista";
    }
}
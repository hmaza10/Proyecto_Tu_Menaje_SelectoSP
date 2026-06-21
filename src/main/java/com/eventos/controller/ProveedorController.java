package com.eventos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.eventos.entity.Proveedor;
import com.eventos.service.ProveedorService;

@Controller
@RequestMapping("/gestionproveedor")
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    @GetMapping("/lista")
    public String listar(Model model) {
        model.addAttribute("proveedores", proveedorService.listar());
        return "proveedor/listaProveedores";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("proveedor", new Proveedor());
        return "proveedor/registrarProveedor";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Proveedor proveedor) {

        if(proveedor.getEstado() == null ||
           proveedor.getEstado().isEmpty()) {

            proveedor.setEstado("Activo");
        }

        proveedorService.guardar(proveedor);

        return "redirect:/gestionproveedor/lista";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable int id, Model model) {
        model.addAttribute("proveedor", proveedorService.buscarPorId(id));
        return "proveedor/registrarProveedor";
    }

    @GetMapping("/cambiarEstado/{id}")
    public String cambiarEstado(@PathVariable Integer id) {

        proveedorService.cambiarEstado(id);

        return "redirect:/gestionproveedor/lista";
    }
}

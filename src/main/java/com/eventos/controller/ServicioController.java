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
import com.eventos.entity.Servicio;
import com.eventos.service.ProveedorService;
import com.eventos.service.ServicioService;

@Controller
@RequestMapping("/gestionservicio")
public class ServicioController {

    @Autowired
    private ServicioService servicioService;
    
    @Autowired
    private ProveedorService proveedorService;

    @GetMapping("/lista")
    public String listar(Model model) {
        model.addAttribute("servicios", servicioService.listar());
        return "servicio/listaServicios";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {

        Servicio servicio = new Servicio();

        servicio.setIdProveedor(new Proveedor());

        model.addAttribute("servicio", servicio);
        model.addAttribute("proveedores", proveedorService.listarActivos());

        return "servicio/registrarServicio";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Servicio servicio) {

        if(servicio.getEstado() == null ||
           servicio.getEstado().isEmpty()) {

            servicio.setEstado("Activo");
        }

        servicioService.guardar(servicio);

        return "redirect:/gestionservicio/lista";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable int id,
                         Model model) {

        model.addAttribute(
                "servicio",
                servicioService.buscarPorId(id));

        model.addAttribute(
                "proveedores",
                proveedorService.listarActivos());

        return "servicio/registrarServicio";
    }

    @GetMapping("/cambiarEstado/{id}")
    public String cambiarEstado(@PathVariable Integer id) {

        servicioService.cambiarEstado(id);

        return "redirect:/gestionservicio/lista";
    }
}

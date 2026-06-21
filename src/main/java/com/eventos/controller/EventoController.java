package com.eventos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.eventos.entity.Cliente;
import com.eventos.entity.Evento;
import com.eventos.repository.EventoServicioRepository;
import com.eventos.service.ClienteService;
import com.eventos.service.EventoService;
import com.eventos.service.ServicioService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/gestionevento")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ServicioService servicioService;
    
    @Autowired
    private EventoServicioRepository eventoServicioRepository;
    
    @GetMapping("/lista")
    public String listar(Model model) {
        model.addAttribute("eventos", eventoService.listar());
        return "evento/listaEventos";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {

        model.addAttribute("evento", new Evento());

        model.addAttribute("clientes", clienteService.listarActivos());

        model.addAttribute("servicios", servicioService.listarActivos());

        model.addAttribute("serviciosSeleccionados",
                new ArrayList<Integer>());

        return "evento/registrarEvento";
    }

    @PostMapping("/guardar")
    public String guardar( @ModelAttribute Evento evento, @RequestParam(required = false) List<Integer> serviciosSeleccionados) {

        eventoService.guardarEventoConServicios(
                evento,
                serviciosSeleccionados);

        return "redirect:/gestionevento/lista";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable int id,
                         Model model) {

        Evento evento = eventoService.buscarPorId(id);

        List<Integer> serviciosSeleccionados =
                eventoServicioRepository
                .findByEvento_IdEvento(id)
                .stream()
                .map(es -> es.getServicio().getIdServicio())
                .toList();

        model.addAttribute("evento", evento);

        model.addAttribute("clientes",
                clienteService.listarActivos());

        model.addAttribute("servicios",
                servicioService.listarActivos());

        model.addAttribute("serviciosSeleccionados",
                serviciosSeleccionados);

        return "evento/registrarEvento";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable int id) {

        eventoService.eliminar(id);

        return "redirect:/gestionevento/lista";
    }

    @GetMapping("/agenda")
    public String agenda(Model model) {

        model.addAttribute("eventos", eventoService.listar());

        return "agenda/agendaEventos";
    }
}
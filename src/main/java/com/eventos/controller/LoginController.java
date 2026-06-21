package com.eventos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.eventos.repository.ClienteRepository;
import com.eventos.repository.ContratoRepository;
import com.eventos.repository.EventoRepository;
import com.eventos.repository.ProveedorRepository;
import com.eventos.repository.ServicioRepository;
import com.eventos.repository.UsuarioRepository;

import com.eventos.entity.Evento;
import com.eventos.service.PagoService;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private EventoRepository eventoRepository;

	@Autowired
	private PagoService pagoService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private ServicioRepository servicioRepository;

	@Autowired
	private ProveedorRepository proveedorRepository;

	@Autowired
	private ContratoRepository contratoRepository;
	
	
    @GetMapping("/login")
    public String login() {
        return "login/login";
    }

    @GetMapping("/inicioAdmin")
    public String inicioAdmin(Model model) {

        model.addAttribute("totalUsuarios",
                usuarioRepository.count());

        model.addAttribute("totalServicios",
                servicioRepository.count());

        model.addAttribute("totalProveedores",
                proveedorRepository.count());

        model.addAttribute("totalContratos",
                contratoRepository.count());

        return "dashboard/inicioAdmin";
    }

    @GetMapping("/inicioEmpleado")
    public String inicioEmpleado(Model model) {

        model.addAttribute(
                "totalClientes",
                clienteRepository.count());

        model.addAttribute(
                "totalEventos",
                eventoRepository.count());

        int pagados = 0;
        int pendientes = 0;

        for(Evento evento : eventoRepository.findAll()) {

            double totalPagado =
                    pagoService.obtenerTotalPagadoPorEvento(
                            evento.getIdEvento());

            if(totalPagado >= evento.getPresupuesto()) {

                pagados++;

            } else {

                pendientes++;
            }
        }

        model.addAttribute(
                "pagados",
                pagados);

        model.addAttribute(
                "pendientesPago",
                pendientes);

        return "dashboard/inicioEmpleado";
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {

        session.invalidate();

        return "redirect:/login";
    }
}
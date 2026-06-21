package com.eventos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.eventos.entity.Rol;
import com.eventos.entity.Usuario;
import com.eventos.service.RolService;
import com.eventos.service.UsuarioService;

@Controller
@RequestMapping("/gestionusuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private RolService rolService;

    @GetMapping("/lista")
    public String listar(Model model) {
        model.addAttribute("usuarios", usuarioService.listar());
        return "usuario/listaUsuarios";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {

        Usuario usuario = new Usuario();

        usuario.setRol(new Rol());

        model.addAttribute("usuario", usuario);
        model.addAttribute("roles", rolService.listar());

        return "usuario/registrarUsuario";
    }

    @PostMapping("/guardar")
    public String guardar(
            @RequestParam(required = false) Integer idUsuario,
            @RequestParam String usuario,
            @RequestParam String clave,
            @RequestParam("rol.idRol") Integer idRol) {

        Usuario u;

        if (idUsuario != null && idUsuario > 0) {
            u = usuarioService.buscarPorId(idUsuario);
        } else {
            u = new Usuario();
            u.setEstado("Activo");
        }

        u.setUsuario(usuario);
        u.setClave(clave);

        Rol rol = new Rol();
        rol.setIdRol(idRol);
        u.setRol(rol);

        usuarioService.guardar(u);

        return "redirect:/gestionusuario/lista";
    }
    
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable int id,
                         Model model) {

        model.addAttribute(
                "usuario",
                usuarioService.buscarPorId(id));

        model.addAttribute(
                "roles",
                rolService.listar());

        return "usuario/registrarUsuario";
    }

    @GetMapping("/cambiarEstado/{id}")
    public String cambiarEstado(@PathVariable Integer id) {

        usuarioService.cambiarEstado(id);

        return "redirect:/gestionusuario/lista";
    }
}

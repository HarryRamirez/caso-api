
package com.caso_api.controller;

import com.caso_api.entidad.Usuario;
import com.caso_api.service.UsuarioService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/usuarios")
public class UsuarioController {
    
  @Autowired
    private UsuarioService usuarioService;
  
  
  

 // Renderiza la vista de registro en el navegador
    @GetMapping("/registro")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "registro";
    }

    // Endpoint para registrar un usuario desde el navegador
    @PostMapping(value = "/registro", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String registrarUsuarioFormulario(@ModelAttribute Usuario usuario, Model model) {
        String respuesta = usuarioService.registrarUsuario(usuario);
        model.addAttribute("mensaje", respuesta);
        return "registro";
    }

    // Endpoint para registrar un usuario desde Postman
    @PostMapping(value = "/registro", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> registrarUsuarioJson(@RequestBody Usuario usuario) {
    String respuesta = usuarioService.registrarUsuario(usuario); // Llama al servicio
    
    // Determinar el estado basado en el mensaje de respuesta
    HttpStatus status = "Usuario ya registrado".equals(respuesta) ? HttpStatus.CONFLICT : HttpStatus.OK;
    
    // Devolver el mensaje directamente en el body
    return ResponseEntity.status(status).body(respuesta);
}

    
    
    
    
    
    // Renderiza la vista de login en el navegador
    @GetMapping("/login")
    public String mostrarFormularioLogin() {
        return "login";
    }

    // Endpoint para iniciar sesión desde el navegador
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String iniciarSesionFormulario(@RequestParam String correo, @RequestParam String contrasenia, Model model) {
        String respuesta = usuarioService.iniciarSesion(correo, contrasenia);
        model.addAttribute("mensaje", respuesta);
        return "login";
    }

    // Endpoint para iniciar sesión desde Postman
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> iniciarSesionJson(@RequestBody Map<String, String> datos) {
        String correo = datos.get("correo");
        String contrasenia = datos.get("contrasenia");
        String respuesta = usuarioService.iniciarSesion(correo, contrasenia);
        return ResponseEntity.ok(respuesta);
    }
}

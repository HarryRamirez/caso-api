
package com.caso_api.service;

import com.caso_api.entidad.Usuario;
import com.caso_api.repository.UsuarioRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    // Método para registrar un usuario
    public String registrarUsuario(Usuario usuario) {
        // Verifica si el correo ya existe
        if (usuarioRepository.findByCorreo(usuario.getCorreo()).isPresent()) {
            return "Usuario ya registrado";
        }

        // Guarda el usuario en la base de datos
        usuarioRepository.save(usuario);
        return "Usuario registrado exitosamente";
    }

    // Método para iniciar sesión
    public String iniciarSesion(String correo, String contrasenia) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByCorreo(correo);

        if (usuarioOpt.isEmpty()) {
            return "Usuario no registrado";
        }

        Usuario usuario = usuarioOpt.get();
        if (!usuario.getContrasenia().equals(contrasenia)) {
            return "Contraseña incorrecta";
        }

        return "Inicio de sesión exitoso";
    }
}

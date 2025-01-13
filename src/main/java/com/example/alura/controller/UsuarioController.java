package com.example.alura.controller;

import com.example.alura.model.Rol;
import com.example.alura.model.Usuario;
import com.example.alura.repository.RolRepository;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.bind.annotation.*;
import com.example.alura.repository.UsuarioRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;

    public UsuarioController(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    // Obtener todos los usuarios (GET)
    @GetMapping
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }



    // Actualizar un usuario existente (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario detallesUsuario) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            usuario.setNombre(detallesUsuario.getNombre());
            usuario.setEmail(detallesUsuario.getEmail());
            // No actualizar contraseña directamente aquí sin validación.

            Usuario usuarioActualizado = usuarioRepository.save(usuario);
            return ResponseEntity.ok(usuarioActualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar un usuario por ID (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Crear un nuevo usuario (POST)

    @PostMapping
    public Usuario crearUsuario(@RequestBody Usuario usuario) {
        // Inicializar roles si está vacío
        if (usuario.getRoles() == null) {
            usuario.setRoles(new HashSet<>());
        }

        // Crear o buscar el rol "USER"
        Rol rolUser = rolRepository.findByNombre("USER")
                .orElseGet(() -> rolRepository.save(new Rol("USER")));

        // Asignar el rol al usuario
        usuario.getRoles().add(rolUser);

        // Guardar el usuario
        return usuarioRepository.save(usuario);
    }


    @Configuration
    public class JacksonConfig {

        @Bean
        public Jackson2ObjectMapperBuilder jacksonBuilder() {
            return Jackson2ObjectMapperBuilder.json()
                    .featuresToDisable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        }
    }
}
package com.example.alura.controller;

import com.example.alura.model.Topico;
import com.example.alura.service.TopicoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    private final TopicoService topicoService;

    // Constructor para inyección de dependencias
    public TopicoController(TopicoService topicoService) {
        this.topicoService = topicoService;
    }

    // Registrar un tópico
    @PostMapping
    public ResponseEntity<String> registrarTopico(@RequestBody Topico topico) {
        // Verificar si ya existe un tópico con el mismo título y mensaje
        Optional<Topico> topicoExistente = topicoService.buscarPorTituloYMensaje(topico.getTitulo(), topico.getMensaje());
        if (topicoExistente.isPresent()) {
            return ResponseEntity.badRequest().body("El tópico ya existe.");
        }

        topicoService.guardar(topico);
        return ResponseEntity.ok("Tópico registrado exitosamente.");
    }

    // Listar todos los tópicos
    @GetMapping
    public ResponseEntity<List<Topico>> listarTopicos() {
        List<Topico> topicos = topicoService.listar();
        return ResponseEntity.ok(topicos);
    }

    // Obtener un tópico por su id
    @GetMapping("/{id}")
    public ResponseEntity<Topico> obtenerTopicoPorId(@PathVariable Long id) {
        Optional<Topico> topicoExistente = topicoService.buscarPorId(id);
        if (topicoExistente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(topicoExistente.get());
    }

    // Actualizar un tópico
    @PutMapping("/{id}")
    public ResponseEntity<String> actualizarTopico(@PathVariable Long id, @RequestBody Topico datosActualizados) {
        Optional<Topico> topicoExistente = topicoService.buscarPorId(id);
        if (topicoExistente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Topico topico = topicoExistente.get();
        topico.setTitulo(datosActualizados.getTitulo());
        topico.setMensaje(datosActualizados.getMensaje());
        topico.setAutor(datosActualizados.getAutor());
        topico.setCurso(datosActualizados.getCurso());
        topicoService.guardar(topico);

        return ResponseEntity.ok("Tópico actualizado exitosamente.");
    }

    // Eliminar un tópico
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarTopico(@PathVariable Long id) {
        Optional<Topico> topicoExistente = topicoService.buscarPorId(id);
        if (topicoExistente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        topicoService.eliminar(id);
        return ResponseEntity.ok("Tópico eliminado exitosamente.");
    }
}

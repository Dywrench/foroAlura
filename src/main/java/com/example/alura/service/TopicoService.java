package com.example.alura.service;

import com.example.alura.model.Topico;
import com.example.alura.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TopicoService {

    @Autowired
    private TopicoRepository topicoRepository;

    public List<Topico> listar() {
        return topicoRepository.findAll();
    }

    public Optional<Topico> buscarPorId(Long id) {
        return topicoRepository.findById(id);
    }

    public Topico guardar(Topico topico) {
        return topicoRepository.save(topico);
    }

    public void eliminar(Long id) {
        topicoRepository.deleteById(id);
    }

    public Optional<Topico> buscarPorTituloYMensaje(String titulo, String mensaje) {
        return topicoRepository.findByTituloAndMensaje(titulo, mensaje);
    }
}
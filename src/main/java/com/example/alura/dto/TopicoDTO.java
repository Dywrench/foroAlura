package com.example.alura.dto;

import com.example.alura.model.Topico;

import java.time.LocalDateTime;

public class TopicoDTO {
    private Long id;
    private String titulo;
    private String mensaje;
    private LocalDateTime createdAt;
    private String curso;
    private String autorEmail; // Agregar campo para el nombre del autor

    public TopicoDTO(Topico topico) {
        this.id = topico.getId();
        this.titulo = topico.getTitulo();
        this.mensaje = topico.getMensaje();
        this.curso = topico.getCurso();
        this.createdAt = topico.getCreatedAt();
        this.autorEmail = topico.getAutor() != null ? topico.getAutor().getEmail() : "Desconocido";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getAutorEmail() {
        return autorEmail;
    }

    public void setAutorEmail(String autorEmail) {
        this.autorEmail = autorEmail;
    }
}


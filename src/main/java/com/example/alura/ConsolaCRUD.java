package com.example.alura;

import com.example.alura.dto.TopicoDTO;
import com.example.alura.model.Topico;
import com.example.alura.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.example.alura.repository.TopicoRepository;
import com.example.alura.repository.UsuarioRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component
public class ConsolaCRUD {

    private final UsuarioRepository usuarioRepository;
    private final TopicoRepository topicoRepository;

    private List<TopicoDTO> topicosEnMemoria = new ArrayList<>();

    @Autowired
    public ConsolaCRUD(UsuarioRepository usuarioRepository, TopicoRepository topicoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.topicoRepository = topicoRepository;
    }

    public void iniciar(Scanner scanner) {
        boolean continuar = true;

        while (continuar) {
            System.out.println("\n=== Menú ===");
            System.out.println("1. Crear usuario");
            System.out.println("2. Mostrar usuarios");
            System.out.println("3. Actualizar usuario");
            System.out.println("4. Eliminar usuario");
            System.out.println("5. Crear Tópico");
            System.out.println("6. Salir");
            System.out.print("Selecciona una opción: ");

            try {
                int opcion = scanner.nextInt();
                switch (opcion) {
                    case 1 -> crearEntidad(scanner);
                    case 2 -> leerEntidades();
                    case 3 -> actualizarEntidad(scanner);
                    case 4 -> eliminarEntidad(scanner);
                    case 5 -> crearTopico(scanner);
                    case 6 -> continuar = false;
                    default -> System.out.println("Opción inválida.");
                }
            } catch (Exception e) {
                System.out.println("Entrada inválida. Por favor, ingresa un número.");
                scanner.nextLine();
            }
        }
    }

    private void crearEntidad(Scanner scanner) {
        System.out.println("\n=== Crear Usuario ===");
        System.out.print("Ingrese su primer nombre: ");
        String nombre = scanner.next().trim();  // Lee el nombre

        System.out.print("Ingrese su email: ");
        String email = scanner.next();  // Lee el email

        System.out.print("Ingresa la contraseña: ");
        String password = scanner.next();  // Lee la contraseña

        if (nombre.isEmpty() || email.isEmpty() || password.isEmpty()) {
            System.out.println("Todos los campos son obligatorios.");
            return;
        }

        Usuario nuevoUsuario = new Usuario(nombre, email, password);
        usuarioRepository.save(nuevoUsuario);
        System.out.println("Entidad creada exitosamente: " + nuevoUsuario);
        System.out.println("Recuerde su ID: "+ nuevoUsuario.getId());
    }

    private void leerEntidades() {
        System.out.println("\n=== Leer Entidades ===");
        List<Usuario> usuarios = usuarioRepository.findAll();
        if (usuarios.isEmpty()) {
            System.out.println("No hay entidades registradas.");
        } else {
            usuarios.forEach(usuario -> System.out.println(usuario));
        }
    }

    private void actualizarEntidad(Scanner scanner) {
        System.out.println("\n=== Actualizar Entidad ===");
        System.out.print("Ingresa el ID de la entidad a actualizar: ");
        Long id = scanner.nextLong();

        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            scanner.nextLine(); // Limpiar buffer
            System.out.print("Ingresa el nuevo nombre: ");
            String nuevoNombre = scanner.nextLine();
            usuario.setNombre(nuevoNombre);
            usuarioRepository.save(usuario);
            System.out.println("Entidad actualizada: " + usuario);
        } else {
            System.out.println("Entidad con ID " + id + " no encontrada.");
        }
    }

    private void eliminarEntidad(Scanner scanner) {
        System.out.println("\n=== Eliminar Entidad ===");
        System.out.print("Ingresa el ID de la entidad a eliminar: ");
        Long id = scanner.nextLong();

        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            System.out.println("Entidad eliminada exitosamente.");
        } else {
            System.out.println("Entidad con ID " + id + " no encontrada.");
        }
    }

    // Función para crear un Tópico sin autenticación
    private void crearTopico(Scanner scanner) {

        System.out.println("\n=== Crear Tópico ===");

        // Leer título
        System.out.print("Ingresa el título del tópico: ");
        String titulo = scanner.next();

        // Leer mensaje
        System.out.print("Ingresa el mensaje: ");
        String mensaje = scanner.next();

        // Leer curso
        System.out.print("Ingresa el curso del tópico: ");
        String curso = scanner.next();
        // Leer el correo del autor
        System.out.print("Ingresa el ID del autor: ");

        long id = scanner.nextLong();


        // Buscar al autor por correo
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        if (usuarioOpt.isEmpty()) {
            System.out.println("El usuario con ID " + id + " no existe.");
            return;
        }

        Usuario autor = usuarioOpt.get();
        System.out.println("Autor encontrado: " + autor.getEmail());

        Topico nuevoTopico = new Topico();
        nuevoTopico.setTitulo(titulo);
        nuevoTopico.setMensaje(mensaje);
        nuevoTopico.setCurso(curso);
        nuevoTopico.setCreatedAt(LocalDateTime.now());
        nuevoTopico.setAutor(autor);

        topicoRepository.save(nuevoTopico);

        TopicoDTO topicoDTO = new TopicoDTO(nuevoTopico);
        System.out.println("Tópico creado:");
        System.out.println("ID: " + topicoDTO.getId());
        System.out.println("Título: " + topicoDTO.getTitulo());
        System.out.println("Mensaje: " + topicoDTO.getMensaje());
        System.out.println("Curso: " + topicoDTO.getCurso());
        System.out.println("Fecha de creación: " + topicoDTO.getCreatedAt());
        System.out.println("Autor email: " + topicoDTO.getAutorEmail());
    }

}

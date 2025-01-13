package com.example.alura;

import com.example.alura.config.JwtUtil;  // Asegúrate de importar la clase JwtUtil
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Scanner;

@ComponentScan(basePackages = {"com.example.alura", "com/example/alura/config", "com/example/alura/repository", "com/example/alura/model", "com/example/alura/service","controller"})
// Incluye config en el escaneo
@EnableJpaRepositories("com/example/alura/repository")
@EntityScan(basePackages = {"com/example/alura/model"})
@SpringBootApplication
public class AluraForoApplication implements CommandLineRunner {

	private final ConsolaCRUD consolaCRUD;
	private final JwtUtil jwtUtil;  // Agrega JwtUtil aquí

	@Autowired
	public AluraForoApplication(ConsolaCRUD consolaCRUD, JwtUtil jwtUtil) {
		this.consolaCRUD = consolaCRUD;
		this.jwtUtil = jwtUtil;  // Inyecta JwtUtil
	}
	public static void main(String[] args) {
		SpringApplication.run(AluraForoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Genera y muestra el token en la consola
		Scanner sc = new Scanner(System.in);
		System.out.println("Ingrese su nombre de usuario para generar token");
		String username = sc.nextLine();
		jwtUtil.generateToken(username);  // Llama al método para imprimir el token

		// El resto de la lógica del programa
		Scanner scanner = new Scanner(System.in);
		consolaCRUD.iniciar(scanner);
	}
}

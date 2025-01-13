package com.example.alura.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // Método para generar el JWT
    public static String generateToken(String username) {

        // Generación del token JWT
        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))  // Expiración en 1 hora
                .signWith(SECRET_KEY)
                .compact();

        // Imprimir el token en la consola
        System.out.println("Token generado: " + token);

        // Retornar el token generado
        return token;
    }

    // Método para validar el JWT
    public static boolean validateToken(String token, UserDetails userDetails) {
        try {
            Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
            return true;  // Token válido
        } catch (Exception e) {
            return false;  // Token inválido
        }
    }

    // Método para extraer el nombre de usuario del token JWT
    public static String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Método auxiliar para extraer cualquier tipo de claim (información) del JWT
    public static <T> T extractClaim(String token, ClaimsResolver<T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.resolve(claims);
    }

    // Método para extraer todos los claims del token
    private static Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody();
    }

    // Método para generar y mostrar el token
    public static void generateAndPrintToken(String username) {
        String token = generateToken(username);
        System.out.println("Generated JWT: " + token);  // Imprime el token generado
    }

    // Interfaz funcional para resolver claims
    @FunctionalInterface
    public interface ClaimsResolver<T> {
        T resolve(Claims claims);
    }
}

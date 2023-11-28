/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtHelper {

    private static final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public static String generateToken(String subject) {
        Date expiration = new Date(System.currentTimeMillis() + 3600000); // 1 hora de duración
        System.out.println(Jwts.builder()
                .setSubject(subject)
                .setExpiration(expiration)
                .signWith(secretKey)
                .compact());
        return Jwts.builder()
                .setSubject(subject)
                .setExpiration(expiration)
                .signWith(secretKey)
                .compact();

    }

    public static Claims decodeToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public static void main(String[] args) {
        String aas = generateToken("rivero");
        System.out.println(aas);
        System.out.println("a - " + decodeToken(aas));
    }
}

package com.example.PG.s.Dragons.security;

import com.example.PG.s.Dragons.entities.User;
import com.example.PG.s.Dragons.exceptions.UnauthorizedException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
@PropertySource("application.properties")
public class JwtTools {
    @Value("${spring.jwt.secret}")
    private String secret;
    @Value("${spring.jwt.expiration}")
    private String expiration;

    public String createToken(User user){
        return "Bearer "+ Jwts.builder().subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+Long.parseLong(expiration)))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes())).compact();
    }
    public void validateToken(String token) throws UnauthorizedException {
        try{
            Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).build().parse(token);
        }catch(Exception e){
            throw new UnauthorizedException("Your token is expired or messed");
        }
    }
    public String extractUsernameFromToken(String token){
        return Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).build()
                .parseSignedClaims(token).getPayload().getSubject();
    }

}

package com.example.Notes.security_manager;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@NoArgsConstructor
public class JwtUtil {

    @Value("${jwt.secret}")
    private String JWT_SECRET;

    public String generateToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", userDetails.getAuthorities());
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String userName){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 604800000))
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET)
                .compact();
    }

    private Claims parseClaims(String token){
        return Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody();
    }

    public String extractRole(String token){
        return parseClaims(token).get("role").toString();
    }

//    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
//        final Claims claims = parseClaims(token);
//        return claimsResolver.apply(claims);
//    }

    public String extractUserName(String token){
        return parseClaims(token).getSubject();
    }

    public Date extractDate(String token){
        return parseClaims(token).getExpiration();
    }

    public boolean isTokenExpired(String token){
        return parseClaims(token).getExpiration().before(new Date());
    }

    public boolean validateToken(String token, UserDetails userDetails){
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}

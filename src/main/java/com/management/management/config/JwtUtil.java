package com.management.management.config;

import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {
    private String secretKey;


    public JwtUtil() {
        secretKey= System.getenv("JWT_SECRET_KEY");
        if(secretKey==null || secretKey.isEmpty()){
            throw new RuntimeException("JWT_KEY is not  in environment");
        }






    }




    public String generateToken(String name){
        Map<String,Object> claims=new HashMap<>();
        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(name)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+1000*60*60))
                .and()
                 .signWith(getKey())
                .compact();

    }
    private SecretKey getKey(){
        byte[]key= Base64.getDecoder().decode(secretKey);
        return Keys.hmacShaKeyFor(key);

    }


    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T>T extractClaim(String token, Function<Claims,T>claimResolver) {
        final Claims claims=exractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims exractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build().
                parseSignedClaims(token).
                getPayload();
    }

    public boolean validationToken(String token, UserDetails userDetails) {
        final String userName=extractUserName(token);
        return (userName.equals(userDetails.getUsername())&& !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token){
        return extractExprition(token).before(new Date());
    }

    private Date extractExprition(String token) {
        return extractClaim(token,Claims::getExpiration);

    }


}

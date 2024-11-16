package com.workflow.util;

import com.workflow.exception.UnAuthoriseException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.setExpirationTimeInMs}")
    private int expirationTime;

    @Value("${jwt.setRefreshExpirationDateInMs}")
    private String refreshExpirationTime;

    private Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    public String extractUsername(String token){
        return extractClaims(token, Claims::getSubject);
    }

    public Date extractExpiration(String token){
        return extractClaims(token, Claims::getExpiration);
    }

    public boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    private <T> T extractClaims(String token, Function<Claims,T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims  extractAllClaims(String token){
        return Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token).getBody();
    }

    public String getToken(UserDetails user){
        final Map<String,Object> claims  =  new HashMap<>();
        return doGenerateToken(claims, user.getUsername());
    }

    private String doGenerateToken(Map<String,Object> claims, String username){
        return Jwts
                   .builder()
                   .setClaims(claims)
                   .setSubject(username)
                   .setIssuedAt(new Date(System.currentTimeMillis()))
                   .setExpiration(new Date(System.currentTimeMillis() + (expirationTime * 10)))
                   .signWith(key(), SignatureAlgorithm.HS256).compact();
    }

    public String getRefreshToken(UserDetails user) throws Exception{
        final Map<String,Object> claims  =  new HashMap<>();
        return doGenerateRefreshToken(claims, user.getUsername());
    }

    private String doGenerateRefreshToken(Map<String,Object> claims, String username) throws Exception{
        Long expTime = Long.parseLong(refreshExpirationTime);
        final Date createdDate = new Date();
        final Date expiryDate = new Date(createdDate.getTime() + (expTime * 30)); 

        List<Map<String,Object>> claimss = new ArrayList<>();
        Map<String,Object> claim = new HashMap<>();
        claim.put("authority", "RERRESH");
        claimss.add(claim);

       return Jwts
                   .builder()
                   .setClaims(claim)
                   .setSubject(username)
                   .setIssuedAt(createdDate)
                   .setExpiration(expiryDate)
                   .signWith(key(), SignatureAlgorithm.HS256).compact();

    }

    public boolean validateToken(String token) throws Exception{
        if(isTokenExpired(token)){
            throw new UnAuthoriseException("Token is Expired");
        }
        return true;
    }
    
}


package com.LibManagementSystem.LibManagementSystem.service.AuthManagementService;

import com.LibManagementSystem.LibManagementSystem.models.UserRelated.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

//this class handles structuring, building, extracting data related to JWT
@Service
public class JwtService {


    @Value("${application.env.jwt.secret-key}")
    private String SECRET_KEY;

    @Value("${application.env.jwt.life-span}")
    private long tokenLifeSpan;


    @Value("${application.env.jwt.refresh-life-span}")
    private Long refreshTokenLifeSpan;

    public String generateToken(User user) {
        return structureToken(user);

    }

    public String generateRefreshToken(User user) {
        return Jwts.builder().setClaims(new HashMap<>()).setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis())).
                setExpiration(new Date(System.currentTimeMillis() + refreshTokenLifeSpan)).signWith(getSignInKey(),
                        SignatureAlgorithm.HS256).compact();
    }


    private String structureToken(User user) {
        return Jwts.builder().setClaims(new HashMap<>()).setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis())).
                setExpiration(new Date(System.currentTimeMillis() + tokenLifeSpan)).signWith(getSignInKey(),
                        SignatureAlgorithm.HS256).compact();
    }

    private Key getSignInKey() {
        //convert string to byte and parse into hmacShaKeyFor to generate a Key
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims allClaims = extractAllClaims(token);
        return claimsResolver.apply(allClaims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().
                setSigningKey(getSignInKey()).
                build().parseClaimsJws(token).getBody();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userEmail = extractUserEmail(token);
        return (userEmail.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String extractUserEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}

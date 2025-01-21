package com.example.sofilove.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.sofilove.Usuario.domain.UsuarioDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;


    private final UsuarioDetailsServiceImpl usuarioService;

    @Autowired
    public JwtService(UsuarioDetailsServiceImpl usuarioService) {
        this.usuarioService = usuarioService;
    }


    public String extractUsername(String token) {return JWT.decode(token).getSubject();}

    public String generateToken(UserDetails data) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + 1000 * 60 * 60 * 24);

        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create()
                .withSubject(data.getUsername())
                .withClaim("role", data.getAuthorities().toArray()[0].toString())
                .withIssuedAt(now)
                .withExpiresAt(expiration)
                .sign(algorithm);
    }

    public void validateToken(String token, String email) throws AuthenticationException {
        JWT.require(Algorithm.HMAC256(secret)).build().verify(token);
        UserDetails userDetails = usuarioService.userDetailsService().loadUserByUsername(email);

        SecurityContext context = SecurityContextHolder.createEmptyContext();

        context.setAuthentication(new UsernamePasswordAuthenticationToken(
                userDetails, token, userDetails.getAuthorities()));

        SecurityContextHolder.setContext(context);

    }

}

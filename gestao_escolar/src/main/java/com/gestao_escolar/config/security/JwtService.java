package com.gestao_escolar.config.security;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.gestao_escolar.model.entity.UsuarioEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.Instant;

@Service
public class JwtService {

    @Value("${SECRET_KEY}")
    private String SECRET_KEY;

    @Value("${EXPIRATION_TIME}")
    private Long EXPIRATION_TIME;

    public String generateToken(UsuarioEntity usuario){
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            String token = JWT.create()
                    .withIssuer("auth-api-gestao-escolar")
                    .withSubject(usuario.getLogin())
                    .withClaim("role",usuario.getPapel().getDescricao())
                    .withIssuedAt(Instant.now())
                    .withExpiresAt(getExpirationDate())
                    .sign(algorithm);

            return token;

        }catch (JWTCreationException e){
            throw new RuntimeException("Erro ao gerar o token JWT: " + e.getMessage());
        }

    }

    public String validadeToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            return JWT.require(algorithm)
                    .withIssuer("auth-api-gestao-escolar")
                    .build()
                    .verify(token)
                    .getSubject();

        }catch (JWTCreationException e){
            return "";
        }
    }

    private Instant getExpirationDate(){
        return Instant.now().plusSeconds(EXPIRATION_TIME);
    }



}

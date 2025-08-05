package com.gestao_escolar.config.security;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    @Value("${jwt.expiration_ms}")
    private Long EXPIRATION_TIME;



}

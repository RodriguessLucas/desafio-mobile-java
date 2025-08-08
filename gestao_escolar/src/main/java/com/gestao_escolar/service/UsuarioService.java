package com.gestao_escolar.service;

import com.gestao_escolar.config.security.JwtService;
import com.gestao_escolar.model.dto.ReqAutenticacaoDTO;
import com.gestao_escolar.model.dto.ReqRegistroDTO;
import com.gestao_escolar.model.dto.ResAutenticacaoDTO;
import com.gestao_escolar.model.entity.UsuarioEntity;
import com.gestao_escolar.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public ResAutenticacaoDTO logar(ReqAutenticacaoDTO autenticacaoDTO){
        var senhaUsuario = new UsernamePasswordAuthenticationToken(autenticacaoDTO.login(), autenticacaoDTO.senha());
        var autenticacao = authenticationManager.authenticate(senhaUsuario);

        var token = jwtService.generateToken((UsuarioEntity) autenticacao.getPrincipal());
        var user = (UsuarioEntity) autenticacao.getPrincipal();

        return new ResAutenticacaoDTO(token, user.getLogin(), user.getPapel());
    }

    public void registrar(ReqRegistroDTO reqRegistroDTO) {
        if (this.usuarioRepository.findByLogin(reqRegistroDTO.login()) != null) {
            throw new RuntimeException("Login já cadastrado.");
        }
        String senhaCripto = bCryptPasswordEncoder.encode(reqRegistroDTO.senha());
        UsuarioEntity novoUsuario = new UsuarioEntity(reqRegistroDTO.login(), senhaCripto, reqRegistroDTO.nome(),reqRegistroDTO.papel());
        usuarioRepository.save(novoUsuario);
    }

}

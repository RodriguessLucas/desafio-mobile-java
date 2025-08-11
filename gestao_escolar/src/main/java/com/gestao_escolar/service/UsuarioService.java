package com.gestao_escolar.service;

import com.gestao_escolar.config.security.JwtService;
import com.gestao_escolar.model.dto.*;
import com.gestao_escolar.model.entity.UsuarioEntity;
import com.gestao_escolar.model.enums.PapelEnum;
import com.gestao_escolar.repository.NotaRepository;
import com.gestao_escolar.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private NotaRepository  notaRepository;

    public ResAutenticacaoDTO logar(ReqAutenticacaoDTO autenticacaoDTO){
        var senhaUsuario = new UsernamePasswordAuthenticationToken(autenticacaoDTO.login(), autenticacaoDTO.senha());
        var autenticacao = authenticationManager.authenticate(senhaUsuario);

        var token = jwtService.generateToken((UsuarioEntity) autenticacao.getPrincipal());
        var user = (UsuarioEntity) autenticacao.getPrincipal();

        return new ResAutenticacaoDTO(token, user.getLogin(),user.getNome(), user.getPapel());
    }

    public void registrar(ReqRegistroDTO reqRegistroDTO) {
        if (this.usuarioRepository.findByLogin(reqRegistroDTO.login()) != null) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Login já cadastrado."
            );
        }
        String senhaCripto = bCryptPasswordEncoder.encode(reqRegistroDTO.senha());
        UsuarioEntity novoUsuario = new UsuarioEntity(reqRegistroDTO.login(), senhaCripto, reqRegistroDTO.nome(),reqRegistroDTO.papel());
        usuarioRepository.save(novoUsuario);
    }


    public List<ResListAlunoDTO> listarAlunos(){
        var listaAlunos = usuarioRepository.findAllByPapelOrderByNomeAsc(PapelEnum.ALUNO);

        if(listaAlunos.isEmpty()){
            throw  new ResponseStatusException(
                    HttpStatus.NO_CONTENT,
                    "Nenhum Aluno encontrado"
            );
        }
        
        var resposta = new ArrayList<ResListAlunoDTO>();
        
        for (UsuarioEntity usuarioEntity : listaAlunos) {
            var notasAluno = notaRepository.findAllNotasAluno(usuarioEntity.getId());

            ResListAlunoDTO dadosAluno = new ResListAlunoDTO(usuarioEntity, notasAluno);
            resposta.add(dadosAluno);
        }

        return resposta;
    }


}

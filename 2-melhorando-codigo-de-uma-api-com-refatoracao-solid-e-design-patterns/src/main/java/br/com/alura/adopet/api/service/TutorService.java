package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.tutor.AtualizacaoTutorDto;
import br.com.alura.adopet.api.dto.tutor.CadastroTutorDto;
import org.springframework.transaction.annotation.Transactional;

public interface TutorService {
    @Transactional
    void cadastrar(CadastroTutorDto cadastroTutorDto);
    @Transactional
    void atualizar(AtualizacaoTutorDto tutorDto);
}

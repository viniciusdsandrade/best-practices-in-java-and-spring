package br.com.alura.adopet.api.service.impl;

import br.com.alura.adopet.api.dto.tutor.AtualizacaoTutorDto;
import br.com.alura.adopet.api.dto.tutor.CadastroTutorDto;
import br.com.alura.adopet.api.exception.DuplicateEntryException;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.TutorRepository;
import br.com.alura.adopet.api.service.TutorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TutorServiceImpl implements TutorService {

    private final TutorRepository tutorRepository;

    public TutorServiceImpl(TutorRepository tutorRepository) {
        this.tutorRepository = tutorRepository;
    }

    @Override
    @Transactional
    public void cadastrar(CadastroTutorDto cadastroTutorDto) {
        boolean jaCadastrado = tutorRepository.existsByTelefoneOrEmail(cadastroTutorDto.telefone(), cadastroTutorDto.email());

        if (jaCadastrado) throw new DuplicateEntryException("Telefone ou email já cadastrado");

        tutorRepository.save(new Tutor(cadastroTutorDto));
    }

    @Override
    @Transactional
    public void atualizar(AtualizacaoTutorDto tutorDto) {
        Tutor tutor = tutorRepository.getReferenceById(tutorDto.id());
        tutor.atualizarDados(tutorDto);
    }
}

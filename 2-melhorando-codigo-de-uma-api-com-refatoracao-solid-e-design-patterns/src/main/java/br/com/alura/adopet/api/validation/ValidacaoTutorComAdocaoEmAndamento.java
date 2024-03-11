package br.com.alura.adopet.api.validation;

import br.com.alura.adopet.api.dto.adocao.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class ValidacaoTutorComAdocaoEmAndamento implements ValidacaoAdocao{

    private final AdocaoRepository repository;
    private final TutorRepository tutorRepository;

    public ValidacaoTutorComAdocaoEmAndamento(AdocaoRepository repository,
                                              TutorRepository tutorRepository) {
        this.repository = repository;
        this.tutorRepository = tutorRepository;
    }

    @Override
    public void validar(SolicitacaoAdocaoDto dto) {
        List<Adocao> adocoes = repository.findAll();
        Tutor tutor = tutorRepository.getReferenceById(dto.idTutor());
        
        for (Adocao a : adocoes) {
            if (Objects.equals(a.getTutor().getId(), dto.idTutor()) &&
                    Objects.equals(a.getStatus(), StatusAdocao.AGUARDANDO_AVALIACAO)) {
                throw new ValidacaoException("Já existe uma solicitação de adoção para este tutor");
            }
        }
    }
}

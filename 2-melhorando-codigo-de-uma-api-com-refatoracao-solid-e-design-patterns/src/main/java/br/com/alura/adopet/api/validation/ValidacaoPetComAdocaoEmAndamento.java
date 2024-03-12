package br.com.alura.adopet.api.validation;

import br.com.alura.adopet.api.dto.adocao.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class ValidacaoPetComAdocaoEmAndamento implements ValidacaoAdocao {

    private final AdocaoRepository repository;

    public ValidacaoPetComAdocaoEmAndamento(AdocaoRepository repository) {
        this.repository = repository;
    }

    @Override
    public void validar(SolicitacaoAdocaoDto dto) {

        boolean petTemAdocaoEmAndamento = repository
                .existsByPetIdAndStatus(dto.idPet(), StatusAdocao.AGUARDANDO_AVALIACAO);

        if (petTemAdocaoEmAndamento)
            throw new ValidacaoException("pet Já existe uma adoção em andamento para este pet");
    }
}
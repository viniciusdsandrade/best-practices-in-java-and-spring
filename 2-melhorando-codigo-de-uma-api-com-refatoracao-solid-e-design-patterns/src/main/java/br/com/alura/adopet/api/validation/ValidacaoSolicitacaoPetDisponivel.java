package br.com.alura.adopet.api.validation;

import br.com.alura.adopet.api.dto.adocao.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.PetRepository;
import org.springframework.stereotype.Component;

@Component
public class ValidacaoSolicitacaoPetDisponivel implements ValidacaoSolicitacaoAdocao {

    private final PetRepository petRepository;

    public ValidacaoSolicitacaoPetDisponivel(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @Override
    public void validar(SolicitacaoAdocaoDto dto) {
        Pet pet = petRepository.getReferenceById(dto.idPet());
        if (pet.getAdotado()) throw new ValidacaoException("Pet já foi adotado");
    }
}

package br.com.alura.adopet.api.validation;

import br.com.alura.adopet.api.dto.adocao.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.PetRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes para validação de pet disponível")
class ValidacaoPetDisponivelTest {

    @InjectMocks
    private ValidacaoSolicitacaoPetDisponivel validacao;

    @Mock
    private PetRepository petRepository;

    @Mock
    private Pet pet;

    @Mock
    private SolicitacaoAdocaoDto dto;

    @Test
    @DisplayName("Deveria permitir solicitação de adoção de pet")
    void deveriaPermitirSolicitacaoDeAdocaoPet() {

        // ARRANGE
        given(petRepository.getReferenceById(dto.idPet())).willReturn(pet);
        given(pet.getAdotado()).willReturn(false);

        //ASSERT + ACT
        assertDoesNotThrow(() -> validacao.validar(dto));
    }

    @Test
    @DisplayName("Não deveria permitir solicitação de adoção de pet")
    void NaoDeveriaPermitirSolicitacaoDeAdocaoPet() {

        // ARRANGE
        given(petRepository.getReferenceById(dto.idPet())).willReturn(pet);
        given(pet.getAdotado()).willReturn(true);

        //ASSERT + ACT
        assertThrows(ValidacaoException.class, () -> validacao.validar(dto));
    }
}
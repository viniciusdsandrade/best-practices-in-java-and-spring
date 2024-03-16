package br.com.alura.adopet.api.validation;

import br.com.alura.adopet.api.dto.adocao.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.enuns.StatusAdocao;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes para validação de solicitação de adoção por tutor com limite de adoções")
class ValidacaoSolicitacaoTutorComLimiteDeAdocoesTest {

    @InjectMocks
    private ValidacaoSolicitacaoTutorComLimiteDeAdocoes validador;

    @Mock
    private AdocaoRepository adocaoRepository;

    @Mock
    private SolicitacaoAdocaoDto dto;

    @Test
    @DisplayName("Deveria permitir solicitação de adoção de pet")
    void naoDeveriaPermitirSolicitacaoDeAdocaoTutorAtingiuLimiteDe5Adocoes() {

        // ARRANGE
        given(dto.idTutor()).willReturn(1L);
        given(adocaoRepository.countByTutorIdAndStatus(1L, StatusAdocao.REPROVADO)).willReturn(5);

        //ASSERT + ACT
        assertThrows(ValidacaoException.class, () -> validador.validar(dto));
    }


    @Test
    @DisplayName("Não deveria permitir solicitação de adoção de pet")
    void deveriaPermitirSolicitacaoDeAdocaoTutorNaoAtingiuLimiteDe5Adocoes() {

        // ARRANGE
        given(dto.idTutor()).willReturn(1L);
        given(adocaoRepository.countByTutorIdAndStatus(1L, StatusAdocao.APROVADO)).willReturn(4);

        //ASSERT + ACT
        assertDoesNotThrow(() -> validador.validar(dto));
    }

}

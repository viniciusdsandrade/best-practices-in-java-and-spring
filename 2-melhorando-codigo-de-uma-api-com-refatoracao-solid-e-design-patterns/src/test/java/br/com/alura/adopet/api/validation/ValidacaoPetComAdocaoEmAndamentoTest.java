package br.com.alura.adopet.api.validation;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

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

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes para validação de pet com adoção em andamento")
class ValidacaoPetComAdocaoEmAndamentoTest {

    @InjectMocks
    private ValidacaoPetComSolicitacaoAdocaoEmAndamento validacao;

    @Mock
    private AdocaoRepository adocaoRepository;

    @Mock
    private SolicitacaoAdocaoDto dto;

    @Test
    @DisplayName("Deveria permitir solicitação de adoção de pet sem adoção em andamento")
    void deveriaPermitirSolicitacaoDeAdocaoPetSemAdocaoEmAndamento() {
        // ARRANGE
        given(adocaoRepository.existsByPetIdAndStatus(any(Long.class), any(StatusAdocao.class))).willReturn(false);

        // ASSERT + ACT
        assertDoesNotThrow(() -> validacao.validar(dto));
    }

    @Test
    @DisplayName("Não deveria permitir solicitação de adoção de pet com adoção em andamento")
    void naoDeveriaPermitirSolicitacaoDeAdocaoPetComAdocaoEmAndamento() {
        // ARRANGE
        given(adocaoRepository.existsByPetIdAndStatus(any(Long.class), any(StatusAdocao.class))).willReturn(true);

        // ASSERT + ACT
        assertThrows(ValidacaoException.class, () -> validacao.validar(dto));
    }
}
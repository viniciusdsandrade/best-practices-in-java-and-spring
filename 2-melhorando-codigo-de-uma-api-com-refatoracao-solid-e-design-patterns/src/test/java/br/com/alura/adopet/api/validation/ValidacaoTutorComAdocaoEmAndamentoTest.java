package br.com.alura.adopet.api.validation;

import br.com.alura.adopet.api.dto.adocao.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes para validação de tutor com adoção em andamento")
class ValidacaoTutorComAdocaoEmAndamentoTest {

    @InjectMocks
    private ValidacaoTutorComSolicitacaoAdocaoEmAndamento validacao;

    @Mock
    private AdocaoRepository adocaoRepository;

    @Mock
    private TutorRepository tutorRepository;

    @Mock
    private SolicitacaoAdocaoDto dto;

    @Test
    @DisplayName("Deveria permitir solicitação de adoção para tutor sem adoção em andamento")
    void deveriaPermitirSolicitacaoDeAdocaoParaTutorSemAdocaoEmAndamento() {
    }
    
    @Test
    @DisplayName("Não deveria permitir solicitação de adoção para tutor com adoção em andamento")
    void naoDeveriaPermitirSolicitacaoDeAdocaoParaTutorComAdocaoEmAndamento() {
    }
}

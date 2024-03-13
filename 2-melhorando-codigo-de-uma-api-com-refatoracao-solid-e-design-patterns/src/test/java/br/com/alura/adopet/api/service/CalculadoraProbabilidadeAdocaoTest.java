package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.abrigo.CadastroAbrigoDto;
import br.com.alura.adopet.api.dto.pet.CadastroPetDto;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.enuns.ProbabilidadeAdocao;
import br.com.alura.adopet.api.model.enuns.TipoPet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/*
Arrange, Act, Assert (AAA)
O padrão AAA é amplamente utilizado e consiste em três etapas distintas:

    a) Arrange (Preparar): Nesta etapa, são realizadas todas as configurações iniciais necessárias para que o cenário de teste possa ser executado. 
    Isso pode incluir a criação de objetos, definição de variáveis, configuração de ambiente e qualquer outra preparação 
    necessária para que o teste seja executado em um estado específico.

    b) Act (Agir): Nesta fase, a ação que se deseja testar é executada. Pode ser a chamada de um método, 
    uma interação com a interface do usuário ou qualquer outra operação que seja o foco do teste.

    c) Assert (Verificar): Na última etapa, os resultados são verificados em relação ao comportamento esperado. É onde se avalia se o resultado obtido após a ação está de acordo com o que se esperava do teste. Caso haja alguma discrepância entre o resultado real e o esperado, o teste falhará.

Given, When, Then (GWT)
O padrão GWT também é conhecido como padrão BDD (Behavior-Driven Development). Ele foi projetado para criar testes com uma linguagem mais próxima da forma como os cenários são discutidos entre as pessoas que fazem parte do projeto, incluindo pessoas técnicas, como desenvolvedores e testadores, além de clientes e usuários da aplicação. O padrão GWT tem as seguintes etapas:

    a) Given (Dado): Nesta etapa, é descrito o cenário inicial ou o contexto do teste. 
    São definidas as condições iniciais necessárias para a execução do cenário de teste.
    
    b) When (Quando): Aqui, a ação específica que está sendo testada é executada. 
    É a etapa em que a ação do usuário ou do sistema acontece.
    
    c) Then (Então): Nesta etapa, os resultados esperados são verificados. 
    São definidos os critérios de sucesso para o cenário de teste, e o teste é aprovado ou reprovado com base nesses resultados.
 */

@DisplayName("Classe CalculadoraProbabilidadeAdocao")
class CalculadoraProbabilidadeAdocaoTest {

    @Test
    @DisplayName("Deve retornar alta probabilidade quando for gato jovem e leve")
    void deveRetornarAltaProbabilidadeQuandoForGatoJovemELeve() {

        // Arrange
        Abrigo abrigo = new Abrigo(new CadastroAbrigoDto(
                "Abrigo feliz",
                "94999999999",
                "abrigofeliz@email.com.br"
        ));
        Pet pet = new Pet(abrigo, new CadastroPetDto(
                TipoPet.GATO,
                "Miau",
                "Siames",
                4,
                "Cinza",
                4.0f
        ));

        // ACT
        CalculadoraProbabilidadeAdocao calculadora = new CalculadoraProbabilidadeAdocao();
        var probabilidade = calculadora.calcular(pet);

        // ASSERT
        Assertions.assertEquals(ProbabilidadeAdocao.ALTA, probabilidade);
    }

    @Test
    @DisplayName("Deve retornar media probabilidade quando for gato idoso e leve")
    void deveRetornarMediaProbabilidadeQuandoForGatoIdosoELeve() {

        Abrigo abrigo = new Abrigo(new CadastroAbrigoDto(
                "Abrigo feliz",
                "94999999999",
                "abrigofeliz@email.com.br"
        ));
        Pet pet = new Pet(abrigo, new CadastroPetDto(
                TipoPet.GATO,
                "Miau",
                "Siames",
                15,
                "Cinza",
                4.0f
        ));

        CalculadoraProbabilidadeAdocao calculadora = new CalculadoraProbabilidadeAdocao();
        ProbabilidadeAdocao probabilidade = calculadora.calcular(pet);

        Assertions.assertEquals(ProbabilidadeAdocao.MEDIA, probabilidade);
    }
}
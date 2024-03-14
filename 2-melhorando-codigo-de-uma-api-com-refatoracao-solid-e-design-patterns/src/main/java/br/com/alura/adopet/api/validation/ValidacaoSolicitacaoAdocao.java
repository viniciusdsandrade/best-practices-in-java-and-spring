package br.com.alura.adopet.api.validation;

import br.com.alura.adopet.api.dto.adocao.SolicitacaoAdocaoDto;

public interface ValidacaoSolicitacaoAdocao {
    
    void validar(SolicitacaoAdocaoDto dto);
}

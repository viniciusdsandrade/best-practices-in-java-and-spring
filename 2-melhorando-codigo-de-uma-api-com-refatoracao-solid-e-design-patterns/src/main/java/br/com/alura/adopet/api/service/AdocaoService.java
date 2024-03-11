package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.adocao.AprovacaoAdocaoDto;
import br.com.alura.adopet.api.dto.adocao.ReprovacaoAdocaoDto;
import br.com.alura.adopet.api.dto.adocao.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.model.Adocao;
import org.springframework.transaction.annotation.Transactional;

public interface AdocaoService {

    @Transactional
    void solicitar(SolicitacaoAdocaoDto adocao);

    @Transactional
    void aprovar(AprovacaoAdocaoDto adocao);

    @Transactional
    void reprovar(ReprovacaoAdocaoDto adocao);
}

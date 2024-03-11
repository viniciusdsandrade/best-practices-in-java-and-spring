package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.model.Adocao;
import org.springframework.transaction.annotation.Transactional;

public interface AdocaoService {

    @Transactional
    void solicitar(Adocao adocao);

    @Transactional
    void aprovar(Adocao adocao);

    @Transactional
    void reprovar(Adocao adocao);
}

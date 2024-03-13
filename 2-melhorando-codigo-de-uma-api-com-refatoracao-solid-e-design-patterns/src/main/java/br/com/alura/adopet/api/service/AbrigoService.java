package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.abrigo.AbrigoDto;
import br.com.alura.adopet.api.dto.abrigo.CadastroAbrigoDto;
import br.com.alura.adopet.api.dto.pet.PetDto;
import br.com.alura.adopet.api.model.Abrigo;

import java.util.List;

public interface AbrigoService {
    
    List<AbrigoDto> listar();
    
    void cadastrar(CadastroAbrigoDto abrigo);

    List<PetDto> listarPetsDoAbrigo(String idOuNome);

    Abrigo carregarAbrigo(String idOuNome);
}

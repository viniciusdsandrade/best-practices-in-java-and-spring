package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.pet.CadastroPetDto;
import br.com.alura.adopet.api.dto.pet.PetDto;
import br.com.alura.adopet.api.model.Abrigo;

import java.util.List;

public interface PetService {
    
    List<PetDto> buscarPetsDisponiveis();

    void cadastrarPet(Abrigo abrigo, CadastroPetDto dto);
}

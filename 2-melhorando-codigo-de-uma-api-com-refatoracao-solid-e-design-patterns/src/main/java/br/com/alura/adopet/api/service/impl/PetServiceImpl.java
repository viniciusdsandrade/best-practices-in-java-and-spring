package br.com.alura.adopet.api.service.impl;

import br.com.alura.adopet.api.dto.pet.CadastroPetDto;
import br.com.alura.adopet.api.dto.pet.PetDto;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.PetRepository;
import br.com.alura.adopet.api.service.PetService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;

    public PetServiceImpl(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @Override
    public List<PetDto> buscarPetsDisponiveis() {
        return petRepository.findAllByAdotadoFalse()
                .stream()
                .map(PetDto::new)
                .toList();
    }

    @Override
    public void cadastrarPet(Abrigo abrigo, CadastroPetDto dto) {
        petRepository.save(new Pet(abrigo, dto));
    }
}

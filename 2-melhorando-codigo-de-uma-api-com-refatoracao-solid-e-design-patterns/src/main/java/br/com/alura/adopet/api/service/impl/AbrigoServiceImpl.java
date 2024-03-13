package br.com.alura.adopet.api.service.impl;

import br.com.alura.adopet.api.dto.abrigo.AbrigoDto;
import br.com.alura.adopet.api.dto.abrigo.CadastroAbrigoDto;
import br.com.alura.adopet.api.dto.pet.PetDto;
import br.com.alura.adopet.api.exception.DuplicateEntryException;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import br.com.alura.adopet.api.service.AbrigoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AbrigoServiceImpl implements AbrigoService {

    private final AbrigoRepository abrigoRepository;
    private final PetRepository petRepository;

    public AbrigoServiceImpl(AbrigoRepository abrigoRepository,
                             PetRepository petRepository) {
        this.abrigoRepository = abrigoRepository;
        this.petRepository = petRepository;
    }

    @Override
    public List<AbrigoDto> listar() {
        return abrigoRepository
                .findAll()
                .stream()
                .map(AbrigoDto::new)
                .toList();
    }

    @Override
    public void cadastrar(CadastroAbrigoDto dto) {
        boolean jaCadastrado = abrigoRepository.existsByNomeOrTelefoneOrEmail(dto.nome(), dto.telefone(), dto.email());

        if (jaCadastrado)
            throw new DuplicateEntryException("Dados já cadastrados para outro abrigo!");

        abrigoRepository.save(new Abrigo(dto));
    }

    @Override
    public List<PetDto> listarPetsDoAbrigo(String idOuNome) {
        Abrigo abrigo = carregarAbrigo(idOuNome);
        return petRepository.findByAbrigo(abrigo)
                .stream()
                .map(PetDto::new)
                .toList();
    }

    @Override
    public Abrigo carregarAbrigo(String idOuNome) {
        Optional<Abrigo> optional;
        try {
            Long id = Long.parseLong(idOuNome);
            optional = abrigoRepository.findById(id);
        } catch (NumberFormatException exception) {
            optional = Optional.ofNullable(abrigoRepository.findByNome(idOuNome));
        }

        return optional.orElseThrow(() -> new ValidacaoException("Abrigo não encontrado"));
    }

    @Override
    public AbrigoDto buscarPorId(Long id) {
        return abrigoRepository
                .findById(id)
                .map(AbrigoDto::new)
                .orElseThrow(() -> new ValidacaoException("Abrigo não encontrado"));
    }
}
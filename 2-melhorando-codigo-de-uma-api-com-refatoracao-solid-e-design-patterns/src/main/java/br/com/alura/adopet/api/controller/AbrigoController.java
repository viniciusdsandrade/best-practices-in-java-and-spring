package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.abrigo.AbrigoDto;
import br.com.alura.adopet.api.dto.abrigo.CadastroAbrigoDto;
import br.com.alura.adopet.api.dto.pet.CadastroPetDto;
import br.com.alura.adopet.api.dto.pet.PetDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.service.AbrigoService;
import br.com.alura.adopet.api.service.PetService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/abrigo")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AbrigoController {

    private final AbrigoService abrigoService;
    private final PetService petService;

    public AbrigoController(AbrigoService abrigoService,
                            PetService petService) {
        this.abrigoService = abrigoService;
        this.petService = petService;
    }

    @GetMapping
    public ResponseEntity<List<AbrigoDto>> listarTodos() {
        List<AbrigoDto> abrigos = abrigoService.listar();
        return ResponseEntity.ok(abrigos);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<String> cadastrar(@RequestBody @Valid CadastroAbrigoDto abrigo) {
        abrigoService.cadastrar(abrigo);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{idOuNome}/pets")
    public ResponseEntity<List<PetDto>> listarPets(@PathVariable String idOuNome) {
        try {
            List<PetDto> petsDoAbrigo = abrigoService.listarPetsDoAbrigo(idOuNome);
            return ResponseEntity.ok(petsDoAbrigo);
        } catch (ValidacaoException exception) {
            return ResponseEntity.badRequest().build();
        } catch (EntityNotFoundException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{idOuNome}/pets")
    @Transactional
    public ResponseEntity<String> cadastrarPet(@PathVariable String idOuNome, @RequestBody @Valid CadastroPetDto pet) {
        try {
            Abrigo abrigo = abrigoService.carregarAbrigo(idOuNome);
            petService.cadastrarPet(abrigo, pet);
            return ResponseEntity.ok().build();

        } catch (ValidacaoException exception) {
            return ResponseEntity.badRequest().build();
        } catch (EntityNotFoundException exception) {
            return ResponseEntity.notFound().build();
        }
    }
}

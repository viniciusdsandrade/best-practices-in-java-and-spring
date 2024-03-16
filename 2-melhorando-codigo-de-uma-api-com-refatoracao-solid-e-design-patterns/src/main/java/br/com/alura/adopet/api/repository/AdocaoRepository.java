package br.com.alura.adopet.api.repository;

import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.enuns.StatusAdocao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdocaoRepository extends JpaRepository<Adocao, Long> {
    
    boolean existsByPetIdAndStatus(Long petId, StatusAdocao status);

    boolean existsByTutorIdAndStatus(Long tutorId, StatusAdocao status);

    Integer countByTutorIdAndStatus(Long tutorId, StatusAdocao status);
}

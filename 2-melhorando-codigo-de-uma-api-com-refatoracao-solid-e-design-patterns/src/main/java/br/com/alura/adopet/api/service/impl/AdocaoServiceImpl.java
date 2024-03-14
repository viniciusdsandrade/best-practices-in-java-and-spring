package br.com.alura.adopet.api.service.impl;

import br.com.alura.adopet.api.dto.adocao.AprovacaoAdocaoDto;
import br.com.alura.adopet.api.dto.adocao.ReprovacaoAdocaoDto;
import br.com.alura.adopet.api.dto.adocao.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import br.com.alura.adopet.api.service.AdocaoService;
import br.com.alura.adopet.api.validation.ValidacaoSolicitacaoAdocao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class AdocaoServiceImpl implements AdocaoService {

    private final AdocaoRepository repository;
    private final EmailServiceImpl emailServiceImpl;
    private final PetRepository petRepository;
    private final TutorRepository tutorRepository;
    private final List<ValidacaoSolicitacaoAdocao> validacoes;

    public AdocaoServiceImpl(AdocaoRepository repository,
                             EmailServiceImpl emailServiceImpl,
                             PetRepository petRepository,
                             TutorRepository tutorRepository,
                             List<ValidacaoSolicitacaoAdocao> validacoes) {
        this.repository = repository;
        this.emailServiceImpl = emailServiceImpl;
        this.petRepository = petRepository;
        this.tutorRepository = tutorRepository;
        this.validacoes = validacoes;
    }

    @Override
    @Transactional
    public void solicitar(SolicitacaoAdocaoDto dto) {

        Pet pet = petRepository.getReferenceById(dto.idPet());
        Tutor tutor = tutorRepository.getReferenceById(dto.idTutor());

        validacoes.forEach(validacao -> validacao.validar(dto));

        Adocao adocao = new Adocao(tutor, pet, dto.motivo());
        repository.save(adocao);

        emailServiceImpl.enviarEmail(
                pet.getAbrigo().getEmail(),
                "Solicitação de adoção",
                "Olá " + pet.getAbrigo().getNome() + "!\n\nUma solicitação de adoção foi registrada hoje para o pet: " + pet.getNome() + ". \nFavor avaliar para aprovação ou reprovação."
        );
    }

    @Override
    @Transactional
    public void aprovar(AprovacaoAdocaoDto dto) {
        Adocao adocao = repository.getReferenceById(dto.idAdocao());
        adocao.aprovar();

        repository.save(adocao);

        emailServiceImpl.enviarEmail(
                adocao.getTutor().getEmail(),
                "Adoção aprovada",
                "Olá " + adocao.getTutor().getNome() + "!\n\nSua solicitação de adoção para o pet " + adocao.getPet().getNome() + " foi aprovada. \nFavor entrar em contato com o abrigo para combinar a entrega."
        );
    }

    @Override
    @Transactional
    public void reprovar(ReprovacaoAdocaoDto dto) {
        Adocao adocao = repository.getReferenceById(dto.idAdocao());
        adocao.reprovar(dto.justificativa());

        repository.save(adocao);

        emailServiceImpl.enviarEmail(
                adocao.getTutor().getEmail(),
                "Adoção reprovada",
                "Olá " + adocao.getTutor().getNome() + "!\n\nSua solicitação de adoção para o pet " + adocao.getPet().getNome() + " foi reprovada. \nJustificativa: " + dto.justificativa()
        );
    }
}
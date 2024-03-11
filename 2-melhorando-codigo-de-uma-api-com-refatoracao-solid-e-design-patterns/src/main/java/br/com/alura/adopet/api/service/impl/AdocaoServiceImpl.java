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
import br.com.alura.adopet.api.validation.ValidacaoAdocao;
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
    private final JavaMailSender emailSender;
    private final PetRepository petRepository;
    private final TutorRepository tutorRepository;

    @Autowired
    private List<ValidacaoAdocao> validacoes;

    public AdocaoServiceImpl(AdocaoRepository repository,
                             JavaMailSender emailSender,
                             PetRepository petRepository,
                             TutorRepository tutorRepository) {
        this.repository = repository;
        this.emailSender = emailSender;
        this.petRepository = petRepository;
        this.tutorRepository = tutorRepository;
    }

    @Override
    @Transactional
    public void solicitar(SolicitacaoAdocaoDto dto) {

        Pet pet = petRepository.getReferenceById(dto.idPet());
        Tutor tutor = tutorRepository.getReferenceById(dto.idTutor());
        validacoes.forEach(validacao -> validacao.validar(dto));
        Adocao adocao = new Adocao(tutor, pet, dto.motivo());
        repository.save(adocao);
        enviarEmailParaAbrigo(adocao);
    }

    @Override
    @Transactional
    public void aprovar(AprovacaoAdocaoDto dto) {
        Adocao adocao = repository.getReferenceById(dto.idAdocao());
        adocao.aprovar();
        repository.save(adocao);
        enviarEmailAprovacao(adocao);
    }

    @Override
    @Transactional
    public void reprovar(ReprovacaoAdocaoDto dto) {
        Adocao adocao = repository.getReferenceById(dto.idAdocao());
        adocao.reprovar(dto.justificativa());
        repository.save(adocao);
        enviarEmailReprovacao(adocao);
    }

    private void enviarEmailParaAbrigo(Adocao adocao) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom("adopet@email.com.br");
        email.setTo(adocao.getPet().getAbrigo().getEmail());
        email.setSubject("Solicitação de adoção");
        email.setText("Olá " + adocao.getPet().getAbrigo().getNome() + "!\n\nUma solicitação de adoção foi registrada hoje para o pet: " + adocao.getPet().getNome() + ". \nFavor avaliar para aprovação ou reprovação.");
        emailSender.send(email);
    }

    private void enviarEmailAprovacao(Adocao adocao) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom("adopet@email.com.br");
        email.setTo(adocao.getTutor().getEmail());
        email.setSubject("Adoção aprovada");
        email.setText("Parabéns " + adocao.getTutor().getNome() + "!\n\nSua adoção do pet " + adocao.getPet().getNome() + ", solicitada em " + adocao.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + ", foi aprovada.\nFavor entrar em contato com o abrigo " + adocao.getPet().getAbrigo().getNome() + " para agendar a busca do seu pet.");
        emailSender.send(email);
    }

    private void enviarEmailReprovacao(Adocao adocao) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom("adopet@email.com.br");
        email.setTo(adocao.getTutor().getEmail());
        email.setSubject("Adoção reprovada");
        email.setText("Olá " + adocao.getTutor().getNome() + "!\n\nInfelizmente sua adoção do pet " + adocao.getPet().getNome() + ", solicitada em " + adocao.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + ", foi reprovada pelo abrigo " + adocao.getPet().getAbrigo().getNome() + " com a seguinte justificativa: " + adocao.getJustificativaStatus());
        emailSender.send(email);
    }
}
package br.com.alura.adopet.api.service.impl;

import br.com.alura.adopet.api.repository.AbrigoRepository;
import br.com.alura.adopet.api.service.AbrigoService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class AbrigoServiceImpl implements AbrigoService {

    private final AbrigoRepository repository;
    private final JavaMailSender mailSender;

    public AbrigoServiceImpl(AbrigoRepository repository, JavaMailSender mailSender) {
        this.repository = repository;
        this.mailSender = mailSender;
    }
}
package edu.icet.solorent.service.impl;

import edu.icet.solorent.dto.Massage;
import edu.icet.solorent.entity.MassageEntity;
import edu.icet.solorent.repository.MassageRepository;
import edu.icet.solorent.service.MassageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MassageServiceImpl implements MassageService {
    private final MassageRepository repository;
    private final ModelMapper mapper;
    private final JavaMailSender mailSender;

    private String receiverEmail = "bitzlk01@gmail.com";

    @Override
    public void add(Massage message) {
        repository.save(mapper.map(message, MassageEntity.class));

        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(receiverEmail);
            mailMessage.setSubject("New Contact Message from " + message.getName());
            mailMessage.setText("Name: " + message.getName() +
                    "\nEmail: " + message.getEmail() +
                    "\nPhone: " + message.getContact() +
                    "\n\nMessage:\n" + message.getMassage());
            mailSender.send(mailMessage);
        } catch (Exception e) {
            log.error("Error sending email: " + e.getMessage());
        }

    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<Massage> getAll() {
        List<MassageEntity> entity = repository.findAll();
        List<Massage> list = new ArrayList<>();
        entity.forEach(el ->list.add(mapper.map(el, Massage.class)));
        return list;
    }
}

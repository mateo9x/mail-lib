package com.mateo9x.mailmicroservice.services;

import com.mateo9x.mailmicroservice.kafka.KafkaMailDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;

    public void handleMailMessage(KafkaMailDto kafkaMailDto) {
        try {
            if (kafkaMailDto.getTemplatePath() != null) {
                    //TODO Implement template load
//                javaMailSender.send(mimeMessage);
            } else {
                SimpleMailMessage mailMessage = new SimpleMailMessage();
                mailMessage.setFrom(kafkaMailDto.getFrom());
                mailMessage.setTo(kafkaMailDto.getTo());
                mailMessage.setSubject(kafkaMailDto.getSubject());
                mailMessage.setText(kafkaMailDto.getMessage());

                javaMailSender.send(mailMessage);
            }
        } catch (MailException mailException) {
            log.warn("Mail has not been send: {}", mailException.getMessage(), mailException);
        }
    }
}

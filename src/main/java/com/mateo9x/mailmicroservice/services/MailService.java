package com.mateo9x.mailmicroservice.services;

import com.mateo9x.mailmicroservice.kafka.KafkaMailDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;
    private final MailTemplateService templateService;

    public void handleMailMessage(KafkaMailDto kafkaMailDto) {
        try {
            if (kafkaMailDto.getTemplatePath() != null) {
                MimeMessage mimeMessage = javaMailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
                helper.setFrom(kafkaMailDto.getFrom());
                helper.setTo(kafkaMailDto.getTo());
                helper.setSubject(kafkaMailDto.getSubject());
                helper.setText(templateService.getTemplate(kafkaMailDto.getTemplatePath(), kafkaMailDto.getReplacementStrings()), true);

                javaMailSender.send(mimeMessage);
            } else {
                SimpleMailMessage mailMessage = new SimpleMailMessage();
                mailMessage.setFrom(kafkaMailDto.getFrom());
                mailMessage.setTo(kafkaMailDto.getTo());
                mailMessage.setSubject(kafkaMailDto.getSubject());
                mailMessage.setText(kafkaMailDto.getMessage());

                javaMailSender.send(mailMessage);
            }
        } catch (MailException | MessagingException exception) {
            log.warn("Mail has not been send: {}", exception.getMessage(), exception);
        }
    }
}

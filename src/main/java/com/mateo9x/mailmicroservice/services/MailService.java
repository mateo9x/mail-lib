package com.mateo9x.mailmicroservice.services;

import com.mateo9x.mailmicroservice.kafka.KafkaMailDto;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;

    public void handleMailMessage(KafkaMailDto kafkaMailDto) {

    }
}

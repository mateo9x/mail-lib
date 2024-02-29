package com.mateo9x.mailmicroservice.kafka;

import lombok.Value;
import org.springframework.mail.MailMessage;

@Value
public class KafkaMailDto {
    String from;
    String to;
    String subject;
    String message;
    MailMessage mailType;
}

package com.mateo9x.mailmicroservice.kafka;

import lombok.*;

@Getter
@NoArgsConstructor
public class KafkaMailDto {
    private String from;
    private String to;
    private String subject;
    private String message;
    private String templatePath;
}

package com.mateo9x.mailmicroservice.kafka;

import com.mateo9x.mailmicroservice.services.MailService;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
@AllArgsConstructor
public class KafkaConsumerConfig {

    private final MailService mailService;

    @Bean
    public ConsumerFactory<String, Object> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "localhost:9092");
        props.put(
                ConsumerConfig.GROUP_ID_CONFIG,
                "email");
        props.put(
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class);
        props.put(
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object>
    kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    @Bean
    public NewTopic topic() {
        return new NewTopic("emails", 2, (short) 1);
    }

    @KafkaListener(
            topics = "emails")
    public void listenWithFilter(ConsumerRecord<String, KafkaMailDto> message,
                                 @Header(KafkaHeaders.RECEIVED_PARTITION) int partition) {
        System.out.println(String.format("Received message partition: %d in filtered listener: %s ", partition, message));
        mailService.handleMailMessage(message.value());
    }
}
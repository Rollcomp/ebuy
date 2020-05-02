package org.ebuy.kafka;

import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.ebuy.dto.MailDto;
import org.ebuy.model.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ozgur Ustun on May, 2020
 */
@Configuration
@EnableKafka
public class SenderConfig  {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public ProducerFactory<String, MailDto> producerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    public KafkaTemplate<String, MailDto> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

}

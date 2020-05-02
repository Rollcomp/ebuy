package org.ebuy.kafka;

import org.ebuy.dto.MailDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

/**
 * Created by Ozgur Ustun on May, 2020
 */
@Service
public class Sender {

    private static final Logger LOGGER = LoggerFactory.getLogger(Sender.class);

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.topic.userRegistered}")
    private String topic;

    @Autowired
    private KafkaTemplate<String, MailDto> kafkaTemplate;

    public void send(MailDto mailDto) {
        Message<MailDto> message = MessageBuilder.withPayload(mailDto).setHeader(KafkaHeaders.TOPIC, topic).build();
        LOGGER.debug("sended message" + message);
        LOGGER.debug("message payload" + message.getPayload().toString());
        LOGGER.debug("message header" + message.getHeaders().toString());
        kafkaTemplate.send(message);
    }

}

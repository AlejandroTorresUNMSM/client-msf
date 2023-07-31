package com.atorres.nttdata.clientmsf.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * .
 * Productor Kafka
 */
@Component
public class KafkaStringProducer {
  /**
   * .
   * Logger
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(KafkaStringProducer.class);
  /**
   * .
   * Kafka template
   */
  private final KafkaTemplate<String, String> kafkaTemplate;

  /**
   * .
   * Producer kafka constructor
   *
   * @param kafkaTemplate kafkaTemplate
   */
  public KafkaStringProducer(
      @Qualifier("kafkaStringTemplate") KafkaTemplate<String, String> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  /**
   * .
   * Metodo que envia el evento
   *
   * @param message mensaje
   */
  public void sendMessage(String message) {
    LOGGER.info("Producing message {}", message);
    this.kafkaTemplate.send("bootcamp-topic", message);
  }

}
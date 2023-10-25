package com.banque.client.service.config;

import com.banque.common.service.event.CompteEvent;
import com.banque.common.service.event.TransactionEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class Consumer {
    @Bean
    public ConsumerFactory<String, CompteEvent> CompteEventConsumer() {

        // HashMap to store the configurations
        Map<String, Object> map
                = new HashMap<>();

        // put the host IP in the map
        map.put(ConsumerConfig
                        .BOOTSTRAP_SERVERS_CONFIG,
                "localhost:19092");

        map.put(ConsumerConfig
                        .KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class);
        map.put(ConsumerConfig
                        .VALUE_DESERIALIZER_CLASS_CONFIG,
                JsonDeserializer.class);

        // return message in JSON formate
        return new DefaultKafkaConsumerFactory<>(
                map, new StringDeserializer(),
                new JsonDeserializer<>(CompteEvent.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String,CompteEvent> CompteEventListner()
    {
        ConcurrentKafkaListenerContainerFactory<String,
                CompteEvent>
                factory
                = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(CompteEventConsumer());
        return factory;
    }

    // transaction

    @Bean
    public ConsumerFactory<String, TransactionEvent> TransactionEventConsumer() {

        // HashMap to store the configurations
        Map<String, Object> map
                = new HashMap<>();

        // put the host IP in the map
        map.put(ConsumerConfig
                        .BOOTSTRAP_SERVERS_CONFIG,
                "localhost:19092");

        map.put(ConsumerConfig
                        .KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class);
        map.put(ConsumerConfig
                        .VALUE_DESERIALIZER_CLASS_CONFIG,
                JsonDeserializer.class);

        // return message in JSON formate
        return new DefaultKafkaConsumerFactory<>(
                map, new StringDeserializer(),
                new JsonDeserializer<>(TransactionEvent.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String,TransactionEvent> TransactionEventListner()
    {
        ConcurrentKafkaListenerContainerFactory<String,
                TransactionEvent>
                factory
                = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(TransactionEventConsumer());
        return factory;
    }



}

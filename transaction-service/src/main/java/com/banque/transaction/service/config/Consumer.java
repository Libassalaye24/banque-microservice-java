package com.banque.transaction.service.config;


import com.banque.common.service.event.ClientEvent;
import com.banque.common.service.event.PaiementEvent;
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

    // Function to establish a connection
    // between Spring application
    // and Kafka server
    @Bean
    public ConsumerFactory<String, ClientEvent> ClientEventConsumer() {

        // HashMap to store the configurations
        Map<String, Object> map
                = new HashMap<>();

        // put the host IP in the map
        map.put(ConsumerConfig
                        .BOOTSTRAP_SERVERS_CONFIG,
                "localhost:29092");

        // put the group ID of consumer in the map
        map.put(ConsumerConfig
                        .KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class);
        map.put(ConsumerConfig
                        .VALUE_DESERIALIZER_CLASS_CONFIG,
                JsonDeserializer.class);

        // return message in JSON formate
        return new DefaultKafkaConsumerFactory<>(
                map, new StringDeserializer(),
                new JsonDeserializer<>(ClientEvent.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String,
                ClientEvent>
    ClientEventListner()
    {
        ConcurrentKafkaListenerContainerFactory<String,
                ClientEvent>
                factory
                = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(ClientEventConsumer());
        return factory;
    }


    ////  PaiementEvent

    @Bean
    public ConsumerFactory<String, PaiementEvent> PaiementEventConsumer() {

        // HashMap to store the configurations
        Map<String, Object> map
                = new HashMap<>();

        // put the host IP in the map
        map.put(ConsumerConfig
                        .BOOTSTRAP_SERVERS_CONFIG,
                "localhost:29092");

        // put the group ID of consumer in the map
        map.put(ConsumerConfig
                        .KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class);
        map.put(ConsumerConfig
                        .VALUE_DESERIALIZER_CLASS_CONFIG,
                JsonDeserializer.class);

        // return message in JSON formate
        return new DefaultKafkaConsumerFactory<>(
                map, new StringDeserializer(),
                new JsonDeserializer<>(PaiementEvent.class));
    }


    @Bean
    public ConcurrentKafkaListenerContainerFactory<String,
            PaiementEvent>
    PaiementEventListner()
    {
        ConcurrentKafkaListenerContainerFactory<String,
                PaiementEvent>
                factory
                = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(PaiementEventConsumer());
        return factory;
    }
}

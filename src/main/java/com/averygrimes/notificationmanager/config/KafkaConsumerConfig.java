package com.averygrimes.notificationmanager.config;

import com.averygrimes.notificationmanager.pojo.Constants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import javax.inject.Inject;

/**
 * @author Avery Grimes-Farrow
 * Created on: 8/26/19
 * https://github.com/helloavery
 */

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    private com.averygrimes.core.kafka.KafkaConsumerConfig coreKafkaConsumerConfig;
    private ProgramArguments programArguments;

    @Inject
    public void setCoreKafkaConsumerConfig(com.averygrimes.core.kafka.KafkaConsumerConfig coreKafkaConsumerConfig) {
        this.coreKafkaConsumerConfig = coreKafkaConsumerConfig;
    }

    @Inject
    public void setProgramArguments(ProgramArguments programArguments) {
        this.programArguments = programArguments;
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory(){
        return coreKafkaConsumerConfig.buildKafkaListenerContainerFactory(programArguments.getBootstrapAddress(), Constants.KAFKA_GROUP_ID);
    }
}

package com.averygrimes.notificationmanager.config;

import com.averygrimes.kafka.KafkaEnvConfig;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.kafka.core.KafkaAdmin;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Avery Grimes-Farrow
 * Created on: 10/8/19
 * https://github.com/helloavery
 */

@Configuration
public class KafkaTopicConfig {

    private Environment environment;

    @Inject
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public KafkaAdmin admin(){
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaEnvConfig.getKafkaBootstrapAddress(environment));
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic emailNotificationTopic(){
        return new NewTopic("itavery-email", 1, (short) 1);
    }

    @Bean
    public NewTopic auditEventTopic(){
        return new NewTopic("itavery-audit", 1, (short) 1);
    }
}

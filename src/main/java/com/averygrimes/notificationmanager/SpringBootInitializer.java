package com.averygrimes.notificationmanager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

import java.util.Arrays;
import java.util.List;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2019-08-08
 * https://github.com/helloavery
 */

@SpringBootApplication(exclude = KafkaAutoConfiguration.class)
@ComponentScan(basePackages = {"com.averygrimes"})
public class SpringBootInitializer implements ApplicationRunner {

    private static final Logger LOGGER = LogManager.getLogger(SpringBootInitializer.class);

    private final List<String> startupParams = Arrays.asList("bootstrapAddress", "S3bucket", "S3bucketObjectMailgun");

    public static void main(String[] args) {
        SpringApplication.run(SpringBootInitializer.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        startupParams.forEach(param -> {
            if (!args.containsOption(param)) {
                throw new RuntimeException("Application boot failed, missing required param: " + param);
            }
        });
        for (String name : args.getOptionNames()) {
            LOGGER.info("arg-" + name + "=" + args.getOptionValues(name));
        }
    }
}

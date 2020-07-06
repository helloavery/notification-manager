package com.averygrimes.notificationmanager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @author Avery Grimes-Farrow
 * Created on: 2019-08-08
 * https://github.com/helloavery
 */

@SpringBootApplication
public class SpringBootInitializer implements ApplicationRunner {

    private static final Logger LOGGER = LogManager.getLogger(SpringBootInitializer.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringBootInitializer.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        for (String name : args.getOptionNames()) {
            LOGGER.info("arg-" + name + "=" + args.getOptionValues(name));
        }
    }
}

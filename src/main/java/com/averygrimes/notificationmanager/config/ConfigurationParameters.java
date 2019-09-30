package com.averygrimes.notificationmanager.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2019-08-08
 * https://github.com/helloavery
 */

@Component
@ConfigurationProperties
public class ConfigurationParameters extends ProgramArguments{
}

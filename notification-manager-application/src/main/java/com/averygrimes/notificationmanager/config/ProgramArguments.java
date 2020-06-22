package com.averygrimes.notificationmanager.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2019-08-08
 * https://github.com/helloavery
 */

@Getter
@Setter
@NoArgsConstructor
public class ProgramArguments {

    private String bootstrapAddress;
    private String mailgunSecretReference;
    private String mailgunKeyValue;
    private String datasourceHost;
    private int datasourcePort;
    private String auditSchema;
}

package com.averygrimes.notificationmanager.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Avery Grimes-Farrow
 * Created on: 5/13/20
 * https://github.com/helloavery
 */

@Getter
@Setter
@NoArgsConstructor
public class AuditResponse {

    private boolean success;
    private List<String> errors = new ArrayList<>();

}

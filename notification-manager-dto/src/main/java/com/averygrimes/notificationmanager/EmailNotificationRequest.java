package com.averygrimes.notificationmanager;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Avery Grimes-Farrow
 * Created on: 5/13/20
 * https://github.com/helloavery
 */

@Data
public class EmailNotificationRequest implements Serializable {

    private static final long serialVersionUID = -3012076909374034787L;

    private EmailNotificationType emailNotificationType;
    private String contextURL;
    private String recipientEmailAddress;
    private String recipientName;
    private String emailToken;
}

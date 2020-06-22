package com.averygrimes.notificationmanager.exceptions;

import org.springframework.http.HttpStatus;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2019-08-14
 * https://github.com/helloavery
 */

public class InvalidEmailNotificationRequestException extends WebApplicationException {

    private InvalidEmailNotificationRequestException(Response response){
        super(response);
    }

    public static InvalidEmailNotificationRequestException buildResponse(String message){
        Response response = generateResponse(message);
        return new InvalidEmailNotificationRequestException(response);
    }

    private static Response generateResponse(String message){
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("status", HttpStatus.BAD_REQUEST);
        errorResponse.put("message", message);
        return Response.status(HttpStatus.BAD_REQUEST.value()).entity(errorResponse).build();
    }
}

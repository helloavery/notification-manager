package com.averygrimes.notificationmanager.exceptions;

import org.apache.http.HttpStatus;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2019-08-08
 * https://github.com/helloavery
 */

public class EmailSenderException extends WebApplicationException {

    private EmailSenderException(Response response){
        super(response);
    }

    public static EmailSenderException buildResponse(String message){
        Response response = generateResponse(message);
        return new EmailSenderException(response);
    }

    private static Response generateResponse(String message){
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("status", HttpStatus.SC_INTERNAL_SERVER_ERROR);
        errorResponse.put("message", message);
        return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).entity(errorResponse).build();
    }

}

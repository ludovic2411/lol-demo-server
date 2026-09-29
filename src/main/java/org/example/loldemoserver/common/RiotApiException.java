package org.example.loldemoserver.common;

/**
 * Common exception for handling riot api unexpected errors
 */
public class RiotApiException extends RuntimeException {
    public RiotApiException(String message) {
        super(String.format("Issue encountered with riot api: %s",message));
    }
}

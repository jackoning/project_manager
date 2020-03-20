package com.sinyun.server.commons.ssh2.exception;


public class Ssh2Exception extends RuntimeException{

    public Ssh2Exception() {
    }

    public Ssh2Exception(String message) {
        super(message);
    }

    public Ssh2Exception(String message, Throwable cause) {
        super(message, cause);
    }

    public Ssh2Exception(Throwable cause) {
        super(cause);
    }

    public Ssh2Exception(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

package br.com.carlohcs.api.exception;

class MessageNotFoundException extends RuntimeException {
        MessageNotFoundException(String message) {
            super(message);
    }
}

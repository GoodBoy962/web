package ru.tinkoff.sirius.web.exception;

public class NonUniqueUserPhoneException extends RuntimeException {

    public NonUniqueUserPhoneException(String message) {
        super(message);
    }

}

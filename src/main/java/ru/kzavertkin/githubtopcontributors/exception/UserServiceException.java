package ru.kzavertkin.githubtopcontributors.exception;

public class UserServiceException extends RuntimeException {
    public UserServiceException(String message, RuntimeException cause) {
        super(message, cause);
    }
}

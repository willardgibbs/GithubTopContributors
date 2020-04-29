package ru.kzavertkin.githubtopcontributors.exception;

public class RepositoryServiceException extends RuntimeException {
    public RepositoryServiceException(String message, RuntimeException cause) {
        super(message, cause);
    }
}

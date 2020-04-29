package ru.kzavertkin.githubtopcontributors.exception;

public class ContributorServiceException extends RuntimeException {
    public ContributorServiceException(String message, RuntimeException cause) {
        super(message, cause);
    }
}

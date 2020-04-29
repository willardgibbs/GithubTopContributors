package ru.kzavertkin.githubtopcontributors.exception;

public class ContributorNotFoundException extends RuntimeException {
    public ContributorNotFoundException(String message) {
        super(message);
    }
}

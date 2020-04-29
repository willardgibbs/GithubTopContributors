package ru.kzavertkin.githubtopcontributors.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.kzavertkin.githubtopcontributors.exception.ContributorNotFoundException;
import ru.kzavertkin.githubtopcontributors.exception.RepositoryNotFoundException;
import ru.kzavertkin.githubtopcontributors.exception.UserNotFoundException;
import ru.kzavertkin.githubtopcontributors.model.GithubTopContributorsError;

@RestControllerAdvice
public class CommonControllerAdvice {
    private static final String APPLICATION_NAME = "github-client-api";

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public GithubTopContributorsError internalServerError(UserNotFoundException exception) {
        return new GithubTopContributorsError()
                .setApplicationName(APPLICATION_NAME)
                .setErrorCode(HttpStatus.NOT_FOUND.value())
                .setErrorMessage(exception.getMessage());
    }

    @ExceptionHandler(RepositoryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public GithubTopContributorsError internalServerError(RepositoryNotFoundException exception) {
        return new GithubTopContributorsError()
                .setApplicationName(APPLICATION_NAME)
                .setErrorCode(HttpStatus.NOT_FOUND.value())
                .setErrorMessage(exception.getMessage());
    }

    @ExceptionHandler(ContributorNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public GithubTopContributorsError internalServerError(ContributorNotFoundException exception) {
        return new GithubTopContributorsError()
                .setApplicationName(APPLICATION_NAME)
                .setErrorCode(HttpStatus.NOT_FOUND.value())
                .setErrorMessage(exception.getMessage());
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public GithubTopContributorsError internalServerError(Exception exception) {
        return new GithubTopContributorsError()
                .setApplicationName(APPLICATION_NAME)
                .setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .setErrorMessage(exception.getMessage());
    }
}

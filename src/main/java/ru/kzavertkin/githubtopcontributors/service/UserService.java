package ru.kzavertkin.githubtopcontributors.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kzavertkin.githubtopcontributors.client.GithubUserClient;
import ru.kzavertkin.githubtopcontributors.exception.UserNotFoundException;
import ru.kzavertkin.githubtopcontributors.exception.UserServiceException;
import ru.kzavertkin.githubtopcontributors.model.User;

/**
 *
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final GithubUserClient githubClient;

    /**
     * @param userName - repository owner name
     * @return {@link User} describing github api user with login equals userName
     */
    public User getUser(String userName) {
        try {
            log.debug("Try to get user: '{}'", userName);
            return githubClient.getUser(userName)
                    .orElseThrow(() -> new UserNotFoundException("User with name " + userName + " not found"));
        } catch (UserNotFoundException e) {
            log.error("Error '{}'", e.getMessage());
            throw e;
        } catch (RuntimeException e) {
            log.error("Error '{}'", e.getMessage());
            throw new UserServiceException("", e);
        }
    }
}

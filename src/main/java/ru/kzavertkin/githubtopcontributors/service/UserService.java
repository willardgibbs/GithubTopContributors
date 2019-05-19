package ru.kzavertkin.githubtopcontributors.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import ru.kzavertkin.githubtopcontributors.model.User;
import ru.kzavertkin.githubtopcontributors.service.exception.UserNotFoundException;

import java.util.Optional;

/**
 *
 */
@Service
@Slf4j
public class UserService {
    @Value("${github.api.users.url}")
    private String userUrlPrefix;

    private RestTemplate restTemplate;

    public UserService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * @param ownerName - repository owner name
     * @return user class describing github api user with login equals ownerName
     */
    public User getUser(String ownerName) throws UserNotFoundException {
        String url = getUrlForUser(ownerName);
        log.debug("Url for getting user: '{}'", url);
        try {
            return Optional.ofNullable(restTemplate.getForObject(url, User.class))
                    .orElseThrow(UserNotFoundException::new);
        } catch (HttpClientErrorException e) {
            log.error("Error while call " + url, e);
            HttpStatus statusCode = e.getStatusCode();
            if (statusCode == HttpStatus.NOT_FOUND)
                throw new UserNotFoundException();
            else
                throw e;
        }
    }

    /**
     * @param ownerName - repository owner name
     * @return url for getting github api user
     */
    private String getUrlForUser(String ownerName) {
        return userUrlPrefix + ownerName;
    }
}

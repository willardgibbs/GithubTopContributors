package ru.kzavertkin.githubtopcontributors.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.kzavertkin.githubtopcontributors.model.User;

/**
 *
 */
@Service
@Slf4j
public class UserService {
    private static final String USER_URL_PREFIX = "https://api.github.com/users/";

    private RestTemplate restTemplate;

    public UserService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * @param ownerName - repository owner name
     * @return user class describing github api user with login equals ownerName
     */
    public User getUser(String ownerName) {
        String url = getUrlForUser(ownerName);
        User user;
        user = restTemplate.getForObject(url, User.class);
        return user;
    }

    /**
     * @param ownerName - repository owner name
     * @return url for getting github api user
     */
    private String getUrlForUser(String ownerName) {
        return USER_URL_PREFIX + ownerName;
    }
}

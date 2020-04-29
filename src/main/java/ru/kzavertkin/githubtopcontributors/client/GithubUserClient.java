package ru.kzavertkin.githubtopcontributors.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.kzavertkin.githubtopcontributors.model.User;

import java.util.Optional;


@FeignClient(name = "GithubUserClient", url = "${github.api.url}")
public interface GithubUserClient {

    @GetMapping(value = "/users/{userName}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    Optional<User> getUser(@PathVariable("userName") String userName);
}

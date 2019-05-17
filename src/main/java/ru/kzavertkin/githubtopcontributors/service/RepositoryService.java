package ru.kzavertkin.githubtopcontributors.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.kzavertkin.githubtopcontributors.model.Contributor;
import ru.kzavertkin.githubtopcontributors.model.Repository;
import ru.kzavertkin.githubtopcontributors.model.User;

import java.util.List;

/**
 *
 */
@Service
@Slf4j
public class RepositoryService {
    private static final String REPOSITORY_URL_PREFIX = "https://api.github.com/repos/";

    private RestTemplate restTemplate;

    public RepositoryService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * @param user           - user class describing github api user
     * @param repositoryName - repository name
     * @return repository by user and repository name
     */
    public Repository getRepository(User user, String repositoryName) {
        String url = getUrlForRepository(user.getLogin(), repositoryName);
        Repository repository;
        repository = restTemplate.getForObject(url, Repository.class);
        return repository;
    }

    /**
     * @param ownerName      - repository owner name
     * @param repositoryName - repository name
     * @return url for getting github api repository
     */
    private String getUrlForRepository(String ownerName, String repositoryName) {
        return REPOSITORY_URL_PREFIX + ownerName + "/" + repositoryName;
    }

    /**
     * @param repository - repository class describing github api repository
     * @return Contributor list
     */
    public List<Contributor> getContributors(Repository repository) {
        String url = getUrlForListOfContributors(repository.getOwner().getLogin(), repository.getName());
        ResponseEntity<List<Contributor>> rateResponse = restTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Contributor>>() {
                });
        return rateResponse.getBody();
    }

    /**
     * @param ownerName      - repository owner name
     * @param repositoryName - repository name
     * @return url for getting github api list of contributors
     */
    private String getUrlForListOfContributors(String ownerName, String repositoryName) {
        return REPOSITORY_URL_PREFIX + ownerName + "/" + repositoryName +
                "/contributors?q=contributions&order=desc";
    }
}

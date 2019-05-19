package ru.kzavertkin.githubtopcontributors.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import ru.kzavertkin.githubtopcontributors.model.Contributor;
import ru.kzavertkin.githubtopcontributors.model.Repository;
import ru.kzavertkin.githubtopcontributors.model.User;
import ru.kzavertkin.githubtopcontributors.service.exception.ContributorNotFoundException;
import ru.kzavertkin.githubtopcontributors.service.exception.RepositoryNotFoundException;
import ru.kzavertkin.githubtopcontributors.service.exception.UserNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 *
 */
@Service
@Slf4j
public class RepositoryService {
    @Value("${github.api.repos.url}")
    private String repositoryUrlPrefix;

    private RestTemplate restTemplate;

    private UserService userService;

    public RepositoryService(RestTemplate restTemplate, UserService userService) {
        this.restTemplate = restTemplate;
        this.userService = userService;
    }

    /**
     * @param user           - user class describing github api user
     * @param repositoryName - repository name
     * @return repository by user and repository name
     */
    public Repository getRepository(User user, String repositoryName) throws RepositoryNotFoundException {
        String url = getUrlForRepository(user.getLogin(), repositoryName);
        log.debug("Url for getting repository: '{}'", url);
        try {
            return Optional.ofNullable(restTemplate.getForObject(url, Repository.class))
                    .orElseThrow(RepositoryNotFoundException::new);
        } catch (HttpClientErrorException e) {
            log.error("Error while call " + url, e);
            HttpStatus statusCode = e.getStatusCode();
            if (statusCode == HttpStatus.NOT_FOUND)
                throw new RepositoryNotFoundException();
            else
                throw e;
        }
    }

    /**
     * @param ownerName      - repository owner name
     * @param repositoryName - repository name
     * @return url for getting github api repository
     */
    private String getUrlForRepository(String ownerName, String repositoryName) {
        return repositoryUrlPrefix + ownerName + "/" + repositoryName;
    }

    /**
     * @param repository - repository class describing github api repository
     * @return Contributor list
     */
    public List<Contributor> getContributors(Repository repository) throws ContributorNotFoundException {
        String url = getUrlForListOfContributors(repository.getOwner().getLogin(), repository.getName());
        log.debug("Url for getting contributors: '{}'", url);
        try {
            return Optional.ofNullable(restTemplate.exchange(url, HttpMethod.GET, null,
                    new ParameterizedTypeReference<List<Contributor>>() {
                    }).getBody()).orElse(new ArrayList<>());
        } catch (HttpClientErrorException e) {
            log.error("Error while call " + url, e);
            HttpStatus statusCode = e.getStatusCode();
            if (statusCode == HttpStatus.NOT_FOUND)
                throw new ContributorNotFoundException();
            else
                throw e;
        }
    }

    /**
     * @param ownerName      - repository owner name
     * @param repositoryName - repository name
     * @return url for getting github api list of contributors
     */
    private String getUrlForListOfContributors(String ownerName, String repositoryName) {
        return repositoryUrlPrefix + ownerName + "/" + repositoryName +
                "/contributors?q=contributions&order=desc";
    }

    /**
     * @param ownerName          - repository owner name
     * @param repositoryName     - repository name
     * @param limitOfContributor - number of contributors returned
     * @return list of contributors in repository
     */
    public List<Contributor> getTopContributors(String ownerName, String repositoryName, int limitOfContributor)
            throws UserNotFoundException, RepositoryNotFoundException, ContributorNotFoundException {
        log.debug("Try to find user with login = '{}'", ownerName);
        User user = userService.getUser(ownerName);
        log.debug("User with login = '{}' was founded: '{}'", ownerName, user);
        log.debug("Try to find repository with ownerName = '{}' and repositoryName = '{}'", ownerName, repositoryName);
        Repository repository = getRepository(user, repositoryName);
        log.debug("Repository with ownerName = '{}' and repositoryName = '{}' was founded: '{}'", ownerName,
                repositoryName, repository);
        log.debug("Try to find contributors in repository with ownerName = '{}' and repositoryName = '{}'", ownerName,
                repositoryName);
        List<Contributor> contributorList = getContributors(repository);
        log.debug("Founded contributors: '{}'", contributorList);
        return Objects.requireNonNull(contributorList).stream().limit(limitOfContributor).collect(Collectors.toList());
    }

}

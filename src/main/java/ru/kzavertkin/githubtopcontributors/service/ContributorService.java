package ru.kzavertkin.githubtopcontributors.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kzavertkin.githubtopcontributors.client.GithubRepositoryClient;
import ru.kzavertkin.githubtopcontributors.exception.ContributorNotFoundException;
import ru.kzavertkin.githubtopcontributors.exception.ContributorServiceException;
import ru.kzavertkin.githubtopcontributors.model.Contributor;
import ru.kzavertkin.githubtopcontributors.model.Repository;
import ru.kzavertkin.githubtopcontributors.model.User;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 *
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ContributorService {
    private final UserService userService;
    private final RepositoryService repositoryService;
    private final GithubRepositoryClient githubClient;

    /**
     * @param ownerName          - repository owner name
     * @param repositoryName     - repository name
     * @param limitOfContributor - number of contributors returned
     * @return {@link List} of {@link Contributor} of github repository
     */
    public List<Contributor> getContributors(String ownerName, String repositoryName, int limitOfContributor, String order) {
        try {
            User owner = userService.getUser(ownerName);
            Repository repository = repositoryService.getRepository(owner, repositoryName);
            List<Contributor> listOfContributors = githubClient.getListOfContributors(
                    owner.getLogin(), repository.getName(), "contributions", order);
            log.debug("Founded contributors: '{}'", listOfContributors);
            return Optional.ofNullable(listOfContributors)
                    .orElseThrow(() -> new ContributorNotFoundException(""))
                    .stream()
                    .limit(limitOfContributor)
                    .collect(Collectors.toList());
        } catch (RuntimeException e) {
            log.error("Error '{}'", e.getMessage());
            throw new ContributorServiceException("", e);
        }
    }
}

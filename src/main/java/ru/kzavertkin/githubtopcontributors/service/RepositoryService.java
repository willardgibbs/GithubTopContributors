package ru.kzavertkin.githubtopcontributors.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kzavertkin.githubtopcontributors.client.GithubRepositoryClient;
import ru.kzavertkin.githubtopcontributors.exception.RepositoryNotFoundException;
import ru.kzavertkin.githubtopcontributors.exception.RepositoryServiceException;
import ru.kzavertkin.githubtopcontributors.model.Repository;
import ru.kzavertkin.githubtopcontributors.model.User;

/**
 *
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RepositoryService {
    private final GithubRepositoryClient githubClient;

    /**
     * @param owner          - {@link User} class describing github api owner
     * @param repositoryName - repository name
     * @return {@link Repository} by owner and repository name
     */
    public Repository getRepository(User owner, String repositoryName) {
        try {
            String ownerLogin = owner.getLogin();
            log.debug("Try to get repository '{}' of owner '{}'", repositoryName, ownerLogin);
            return githubClient.getRepositoryForOwner(ownerLogin, repositoryName)
                    .orElseThrow(() -> new RepositoryNotFoundException(
                            "repository " + repositoryName + " with owner " + ownerLogin + "not found"));
        } catch (RepositoryNotFoundException e) {
            log.error("Error '{}'", e.getMessage());
            throw e;
        } catch (RuntimeException e) {
            log.error("Error '{}'", e.getMessage());
            throw new RepositoryServiceException("", e);
        }
    }

}

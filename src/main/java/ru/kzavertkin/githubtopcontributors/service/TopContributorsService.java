package ru.kzavertkin.githubtopcontributors.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kzavertkin.githubtopcontributors.model.Contributor;
import ru.kzavertkin.githubtopcontributors.model.Repository;
import ru.kzavertkin.githubtopcontributors.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 *
 */
@Service
@Slf4j
public class TopContributorsService {
    private RepositoryService repositoryService;
    private UserService userService;

    public TopContributorsService(RepositoryService repositoryService, UserService userService) {
        this.repositoryService = repositoryService;
        this.userService = userService;
    }

    /**
     * @param ownerName          - repository owner name
     * @param repositoryName     - repository name
     * @param limitOfContributor - number of contributors returned
     * @return list of contributors in repository
     */
    public List<Contributor> getTopContributors(String ownerName, String repositoryName, int limitOfContributor) {
        User user = userService.getUser(ownerName);
        if (user == null) {
            log.warn("User with username '{}' not found", ownerName);
            return new ArrayList<>();
        }
        Repository repository = repositoryService.getRepository(user, repositoryName);
        if (repository == null) {
            log.warn("Repository with repositoryName '{}' not found", repositoryName);
            return new ArrayList<>();
        }
        List<Contributor> contributorList = repositoryService.getContributors(repository);
        if (contributorList == null) {
            log.warn("Internal error");
            return new ArrayList<>();
        }
        return Objects.requireNonNull(contributorList).stream().limit(limitOfContributor).collect(Collectors.toList());
    }

}

package ru.kzavertkin.githubtopcontributors.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ru.kzavertkin.githubtopcontributors.model.Contributor;
import ru.kzavertkin.githubtopcontributors.service.RepositoryService;
import ru.kzavertkin.githubtopcontributors.service.exception.ContributorNotFoundException;
import ru.kzavertkin.githubtopcontributors.service.exception.RepositoryNotFoundException;
import ru.kzavertkin.githubtopcontributors.service.exception.UserNotFoundException;

import java.util.List;

/**
 *
 */
@RestController
@RequestMapping("/githubapiclient/repos")
@Slf4j
public class RepositoryController {
    private RepositoryService repositoryService;

    public RepositoryController(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    /**
     * @param ownerName      - repository owner name
     * @param repositoryName - repository name
     * @return list of top 3 contributors in repository
     */
    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/{ownerName}/{repositoryName}/contributors/top3")
    public List<Contributor> getTop3Contributors(@PathVariable String ownerName, @PathVariable String repositoryName) {
        try {
            return repositoryService.getTopContributors(ownerName, repositoryName, 3);
        } catch (UserNotFoundException e) {
            log.error("UserNotFoundException was catched, rethrow 404");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User " + ownerName + " is not found", e);
        } catch (RepositoryNotFoundException e) {
            log.error("RepositoryNotFoundException was catched, rethrow 404");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Repository " + repositoryName + " is not found", e);
        } catch (ContributorNotFoundException e) {
            log.error("ContributorNotFoundException was catched, rethrow 404");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Contributors is not found", e);
        }
    }

}

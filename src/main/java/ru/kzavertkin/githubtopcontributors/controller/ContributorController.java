package ru.kzavertkin.githubtopcontributors.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.kzavertkin.githubtopcontributors.model.Contributor;
import ru.kzavertkin.githubtopcontributors.service.ContributorService;

import java.util.List;

/**
 *
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/repos")
public class ContributorController {
    private final ContributorService contributorService;

    /**
     * @param ownerName      - repository owner name
     * @param repositoryName - repository name
     * @return list of contributors in repository
     */
    @GetMapping(value = "/{ownerName}/{repositoryName}/contributors", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Contributor> getContributors(@PathVariable String ownerName, @PathVariable String repositoryName,
                                             @RequestParam String order, @RequestParam int limit) {
        return contributorService.getContributors(ownerName, repositoryName, limit, order);
    }

}

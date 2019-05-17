package ru.kzavertkin.githubtopcontributors.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kzavertkin.githubtopcontributors.model.Contributor;
import ru.kzavertkin.githubtopcontributors.service.TopContributorsService;

import java.util.List;

/**
 *
 */
@RestController
@RequestMapping("/top3contributors")
@Slf4j
public class TopContributorsController {
    private TopContributorsService topContributorsService;

    public TopContributorsController(TopContributorsService topContributorsService) {
        this.topContributorsService = topContributorsService;
    }

    /**
     * @param ownerName      - repository owner name
     * @param repositoryName - repository name
     * @return list of top 3 contributors in repository
     */
    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/{ownerName}/{repositoryName}")
    public List<Contributor> getTop3Contributors(@PathVariable String ownerName, @PathVariable String repositoryName) {
        return topContributorsService.getTopContributors(ownerName, repositoryName, 3);
    }


}

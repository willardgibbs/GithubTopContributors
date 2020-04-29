package ru.kzavertkin.githubtopcontributors.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kzavertkin.githubtopcontributors.model.Contributor;
import ru.kzavertkin.githubtopcontributors.model.Repository;

import java.util.List;
import java.util.Optional;

@FeignClient(name = "GithubRepositoryClient", url = "${github.api.url}")
public interface GithubRepositoryClient {

    @GetMapping(value = "/repos/{ownerName}/{repositoryName}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    Optional<Repository> getRepositoryForOwner(@PathVariable("ownerName") String ownerName,
                                               @PathVariable("repositoryName") String repositoryName);

    @GetMapping(value = "/repos/{ownerName}/{repositoryName}/contributors", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    List<Contributor> getListOfContributors(@PathVariable("ownerName") String ownerName,
                                            @PathVariable("repositoryName") String repositoryName,
                                            @RequestParam("query") String query, @RequestParam("order") String order);
}

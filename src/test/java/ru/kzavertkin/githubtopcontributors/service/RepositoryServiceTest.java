package ru.kzavertkin.githubtopcontributors.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import ru.kzavertkin.githubtopcontributors.model.Contributor;
import ru.kzavertkin.githubtopcontributors.model.Repository;
import ru.kzavertkin.githubtopcontributors.model.User;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
@RunWith(SpringRunner.class)
class RepositoryServiceTest {
    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private RepositoryService repositoryService;

    @Test
    void getRepository() {
        String ownerName = "someusername";
        String repositoryName = "somerep";

        User user = new User();
        user.setLogin(ownerName);

        Repository repository = new Repository();
        repository.setOwner(user);
        repository.setName(repositoryName);

        String url = "https://api.github.com/repos/" + ownerName + "/" + repositoryName;

        doReturn(repository).when(restTemplate).getForObject(url, Repository.class);

        assertEquals(repository, repositoryService.getRepository(user, repositoryName));
    }

    @Test
    void getContributors() {
        String ownerName = "someusername";
        String repositoryName = "somerep";

        User user = new User();
        user.setLogin(ownerName);

        Repository repository = new Repository();
        repository.setOwner(user);
        repository.setName(repositoryName);

        String url = "https://api.github.com/repos/" + ownerName + "/" + repositoryName
                + "/contributors?q=contributions&order=desc";

        List<Contributor> contributorList = new ArrayList<>();
        Contributor contributor = new Contributor();
        contributor.setLogin(ownerName);
        contributor.setContributions(1);
        contributor.setId(123);
        contributorList.add(contributor);

        ResponseEntity<List<Contributor>> listResponseEntity = new ResponseEntity<>(contributorList, HttpStatus.OK);

        doReturn(listResponseEntity).when(restTemplate).exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Contributor>>() {
                });

        assertEquals(contributorList, repositoryService.getContributors(repository));
    }
}
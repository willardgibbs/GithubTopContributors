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
import ru.kzavertkin.githubtopcontributors.service.exception.RepositoryNotFoundException;
import ru.kzavertkin.githubtopcontributors.service.exception.UserNotFoundException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

@SpringBootTest
@RunWith(SpringRunner.class)
class RepositoryServiceTest {
    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private RepositoryService repositoryService;

    @MockBean
    private UserService userService;

    @Test
    void getRepository() throws RepositoryNotFoundException {
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
    void getContributors() throws ContributorNotFoundException {
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

    @Test
    void getTopContributors() throws UserNotFoundException, ContributorNotFoundException, RepositoryNotFoundException {
        String ownerName = "someusername";
        String repositoryName = "somerep";
        int limit = 3;

        List<Contributor> contributorList = getContributorList();

        User user = new User();
        Repository repository = new Repository();
        user.setLogin(ownerName);
        repository.setOwner(user);
        repository.setName(repositoryName);

        RepositoryService repositoryServiceSpy = spy(repositoryService);

        doReturn(user).when(userService).getUser(ownerName);
        doReturn(contributorList).when(repositoryServiceSpy).getContributors(repository);
        doReturn(repository).when(repositoryServiceSpy).getRepository(user, repositoryName);

        List<Contributor> expectedContributorList = new ArrayList<>(contributorList);
        expectedContributorList.remove(3);

        assertEquals(expectedContributorList, repositoryServiceSpy.getTopContributors(ownerName, repositoryName,
                limit));
    }

    private List<Contributor> getContributorList() {
        List<Contributor> contributorList = new ArrayList<>();
        Contributor contributor1 = new Contributor();
        contributor1.setLogin("1");
        contributor1.setContributions(1);
        contributor1.setId(1);

        Contributor contributor2 = new Contributor();
        contributor2.setLogin("2");
        contributor2.setContributions(2);
        contributor2.setId(12);

        Contributor contributor3 = new Contributor();
        contributor3.setLogin("3");
        contributor3.setContributions(3);
        contributor3.setId(123);

        Contributor contributor4 = new Contributor();
        contributor4.setLogin("4");
        contributor4.setContributions(4);
        contributor4.setId(1234);

        contributorList.add(contributor4);
        contributorList.add(contributor3);
        contributorList.add(contributor2);
        contributorList.add(contributor1);

        return contributorList;
    }
}
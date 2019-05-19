package ru.kzavertkin.githubtopcontributors.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
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

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class RepositoryServiceTest {
    @Value("${github.api.repos.url}")
    private String repositoryUrlPrefix;

    @MockBean
    private RestTemplate restTemplate;

    @SpyBean
    private RepositoryService repositoryService;

    @MockBean
    private UserService userService;

    @Test
    public void getRepository() throws RepositoryNotFoundException {
        String ownerName = "someusername";
        String repositoryName = "somerep";

        User user = new User();
        user.setLogin(ownerName);

        Repository repository = new Repository();
        repository.setOwner(user);
        repository.setName(repositoryName);

        String url = repositoryUrlPrefix + ownerName + "/" + repositoryName;

        doReturn(repository).when(restTemplate).getForObject(url, Repository.class);

        assertEquals(repository, repositoryService.getRepository(user, repositoryName));
    }

    @Test(expected = RepositoryNotFoundException.class)
    public void getRepositoryWithNullRepository() throws RepositoryNotFoundException {
        String ownerName = "someusername";
        String repositoryName = "somerep";

        User user = new User();
        user.setLogin(ownerName);

        String url = repositoryUrlPrefix + ownerName + "/" + repositoryName;

        doReturn(null).when(restTemplate).getForObject(url, Repository.class);

        repositoryService.getRepository(user, repositoryName);
    }

    @Test(expected = RepositoryNotFoundException.class)
    public void getRepositoryWithHttpNotFound() throws RepositoryNotFoundException {
        String ownerName = "someusername";
        String repositoryName = "somerep";

        User user = new User();
        user.setLogin(ownerName);

        String url = repositoryUrlPrefix + ownerName + "/" + repositoryName;

        HttpClientErrorException httpClientErrorException = new HttpClientErrorException(HttpStatus.NOT_FOUND);
        doThrow(httpClientErrorException).when(restTemplate).getForObject(url, Repository.class);

        repositoryService.getRepository(user, repositoryName);
    }

    @Test(expected = HttpClientErrorException.class)
    public void getRepositoryWithAnotherHttpStatus() throws RepositoryNotFoundException {
        String ownerName = "someusername";
        String repositoryName = "somerep";

        User user = new User();
        user.setLogin(ownerName);

        String url = repositoryUrlPrefix + ownerName + "/" + repositoryName;

        HttpClientErrorException httpClientErrorException = new HttpClientErrorException(HttpStatus.FORBIDDEN);
        doThrow(httpClientErrorException).when(restTemplate).getForObject(url, Repository.class);

        repositoryService.getRepository(user, repositoryName);
    }

    @Test
    public void getContributors() throws ContributorNotFoundException {
        String ownerName = "someusername";
        String repositoryName = "somerep";

        User user = new User();
        user.setLogin(ownerName);

        Repository repository = new Repository();
        repository.setOwner(user);
        repository.setName(repositoryName);

        String url = repositoryUrlPrefix + ownerName + "/" + repositoryName
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
    public void getContributorsWithNull() throws ContributorNotFoundException {
        String ownerName = "someusername";
        String repositoryName = "somerep";

        User user = new User();
        user.setLogin(ownerName);

        Repository repository = new Repository();
        repository.setOwner(user);
        repository.setName(repositoryName);

        String url = repositoryUrlPrefix + ownerName + "/" + repositoryName
                + "/contributors?q=contributions&order=desc";

        ResponseEntity<List<Contributor>> listResponseEntity = new ResponseEntity<>(null, HttpStatus.OK);

        doReturn(listResponseEntity).when(restTemplate).exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Contributor>>() {
                });

        assertEquals(new ArrayList<>(), repositoryService.getContributors(repository));
    }

    @Test(expected = ContributorNotFoundException.class)
    public void getContributorsWithHttpNotFound() throws ContributorNotFoundException {
        String ownerName = "someusername";
        String repositoryName = "somerep";

        User user = new User();
        user.setLogin(ownerName);

        Repository repository = new Repository();
        repository.setOwner(user);
        repository.setName(repositoryName);

        String url = repositoryUrlPrefix + ownerName + "/" + repositoryName
                + "/contributors?q=contributions&order=desc";

        HttpClientErrorException httpClientErrorException = new HttpClientErrorException(HttpStatus.NOT_FOUND);
        doThrow(httpClientErrorException).when(restTemplate).exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Contributor>>() {
                });

        repositoryService.getContributors(repository);
    }

    @Test(expected = HttpClientErrorException.class)
    public void getContributorsWithAnotherHttpStatus() throws ContributorNotFoundException {
        String ownerName = "someusername";
        String repositoryName = "somerep";

        User user = new User();
        user.setLogin(ownerName);

        Repository repository = new Repository();
        repository.setOwner(user);
        repository.setName(repositoryName);

        String url = repositoryUrlPrefix + ownerName + "/" + repositoryName
                + "/contributors?q=contributions&order=desc";

        HttpClientErrorException httpClientErrorException = new HttpClientErrorException(HttpStatus.FORBIDDEN);
        doThrow(httpClientErrorException).when(restTemplate).exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Contributor>>() {
                });

        repositoryService.getContributors(repository);
    }

    @Test
    public void getTopContributors() throws UserNotFoundException, ContributorNotFoundException, RepositoryNotFoundException {
        String ownerName = "someusername";
        String repositoryName = "somerep";
        int limit = 3;

        List<Contributor> contributorList = getContributorList();

        User user = new User();
        Repository repository = new Repository();
        user.setLogin(ownerName);
        repository.setOwner(user);
        repository.setName(repositoryName);

        doReturn(user).when(userService).getUser(ownerName);
        doReturn(contributorList).when(repositoryService).getContributors(repository);
        doReturn(repository).when(repositoryService).getRepository(user, repositoryName);

        List<Contributor> expectedContributorList = new ArrayList<>(contributorList);
        expectedContributorList.remove(3);

        assertEquals(expectedContributorList, repositoryService.getTopContributors(ownerName, repositoryName,
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
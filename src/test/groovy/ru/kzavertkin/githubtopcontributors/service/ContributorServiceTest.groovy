package ru.kzavertkin.githubtopcontributors.service

import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import ru.kzavertkin.githubtopcontributors.client.GithubRepositoryClient
import ru.kzavertkin.githubtopcontributors.exception.ContributorServiceException
import ru.kzavertkin.githubtopcontributors.model.Contributor
import ru.kzavertkin.githubtopcontributors.model.Repository
import ru.kzavertkin.githubtopcontributors.model.User

import static org.junit.Assert.assertEquals
import static org.mockito.ArgumentMatchers.any
import static org.mockito.ArgumentMatchers.anyString
import static org.mockito.Mockito.*

class ContributorServiceTest {

    @InjectMocks
    ContributorService contributorService

    @Mock
    UserService userService

    @Mock
    RepositoryService repositoryService

    @Mock
    GithubRepositoryClient githubClient

    def repository = new Repository(
            name: "somereponame",
            owner: new User(login: "someusername"))

    @Before
    void setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test(expected = ContributorServiceException.class)
    void "got exception when try to get user for getting contributors from github"() {
        when(userService.getUser(repository.owner.login)).thenThrow(new RuntimeException())
        contributorService.getContributors(repository.owner.login, repository.name, 3, "desc")
        verify(repositoryService, times(0)).getRepository(any(), anyString())
        verify(githubClient, times(0))
                .getListOfContributors(anyString(), anyString(), anyString(), anyString())
    }

    @Test(expected = ContributorServiceException.class)
    void "got exception when try to get repository for getting contributors from github"() {
        when(userService.getUser(repository.owner.login)).thenReturn(repository.owner)
        when(repositoryService.getRepository(repository.owner, repository.name)).thenThrow(new RuntimeException())
        contributorService.getContributors(repository.owner.login, repository.name, 3, "desc")
        verify(githubClient, times(0))
                .getListOfContributors(anyString(), anyString(), anyString(), anyString())
    }

    @Test
    void "got empty list when github contributions return empty list"() {
        when(userService.getUser(repository.owner.login)).thenReturn(repository.owner)
        when(repositoryService.getRepository(repository.owner, repository.name)).thenReturn(repository)
        when(githubClient.getListOfContributors(repository.owner.login, repository.name, "contributions", "desc"))
                .thenReturn([])
        assertEquals([], contributorService.getContributors(repository.owner.login, repository.name, 3, "desc"))
    }

    @Test
    void "got one result when github contributions return one result"() {
        when(userService.getUser(repository.owner.login)).thenReturn(repository.owner)
        when(repositoryService.getRepository(repository.owner, repository.name)).thenReturn(repository)
        when(githubClient.getListOfContributors(repository.owner.login, repository.name, "contributions", "desc"))
                .thenReturn([new Contributor(id: 1)])
        assertEquals([new Contributor(id: 1)],
                contributorService.getContributors(repository.owner.login, repository.name, 3, "desc"))
    }

    @Test
    void "got expected amount of contributors when github contributions return more result than requested"() {
        when(userService.getUser(repository.owner.login)).thenReturn(repository.owner)
        when(repositoryService.getRepository(repository.owner, repository.name)).thenReturn(repository)
        when(githubClient.getListOfContributors(repository.owner.login, repository.name, "contributions", "desc"))
                .thenReturn([new Contributor(id: 1), new Contributor(id: 2), new Contributor(id: 3), new Contributor(id: 4)])
        assertEquals(3,
                contributorService.getContributors(repository.owner.login, repository.name, 3, "desc").size())
    }

}

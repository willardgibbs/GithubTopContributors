package ru.kzavertkin.githubtopcontributors.service

import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import ru.kzavertkin.githubtopcontributors.client.GithubRepositoryClient
import ru.kzavertkin.githubtopcontributors.exception.RepositoryNotFoundException
import ru.kzavertkin.githubtopcontributors.exception.RepositoryServiceException
import ru.kzavertkin.githubtopcontributors.model.Repository
import ru.kzavertkin.githubtopcontributors.model.User

import static org.junit.Assert.assertEquals
import static org.mockito.Mockito.when

class RepositoryServiceTest {

    @InjectMocks
    RepositoryService service

    @Mock
    GithubRepositoryClient client

    def repository = new Repository(
            name: "somereponame",
            owner: new User(login: "someusername"))

    @Before
    void setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    void "success getting repository by repository owner and name"() {
        when(client.getRepositoryForOwner(repository.owner.login, repository.name))
                .thenReturn(Optional.ofNullable(repository))
        assertEquals(repository, service.getRepository(repository.owner, repository.name))
    }

    @Test(expected = RepositoryNotFoundException.class)
    void "got exception when getting repository from github return null"() {
        when(client.getRepositoryForOwner(repository.owner.login, repository.name)).thenReturn(Optional.empty())
        service.getRepository(repository.owner, repository.name)
    }

    @Test(expected = RepositoryServiceException.class)
    void "got exception when got some error while getting repository from github"() {
        when(client.getRepositoryForOwner(repository.owner.login, repository.name)).thenThrow(new RuntimeException())
        service.getRepository(repository.owner, repository.name)
    }
}
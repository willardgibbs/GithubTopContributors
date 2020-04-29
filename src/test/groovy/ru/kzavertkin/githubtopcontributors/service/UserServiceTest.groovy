package ru.kzavertkin.githubtopcontributors.service

import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import ru.kzavertkin.githubtopcontributors.client.GithubUserClient
import ru.kzavertkin.githubtopcontributors.exception.UserNotFoundException
import ru.kzavertkin.githubtopcontributors.exception.UserServiceException
import ru.kzavertkin.githubtopcontributors.model.User

import static org.junit.Assert.assertEquals
import static org.mockito.Mockito.when

class UserServiceTest {

    @InjectMocks
    UserService userService

    @Mock
    GithubUserClient githubClient

    def expectedUser = new User(login: "someusername")

    @Before
    void setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    void "successful getting user by login"() {
        when(githubClient.getUser(expectedUser.login)).thenReturn(Optional.ofNullable(expectedUser))
        assertEquals(expectedUser, userService.getUser(expectedUser.login))
    }

    @Test(expected = UserNotFoundException.class)
    void "got exception when getting user from github return null"() {
        when(githubClient.getUser(expectedUser.login)).thenReturn(Optional.empty())
        userService.getUser(expectedUser.login)
    }

    @Test(expected = UserServiceException.class)
    void "got exception when got some error while getting user from github"() {
        when(githubClient.getUser(expectedUser.login)).thenThrow(new RuntimeException())
        userService.getUser(expectedUser.login)
    }
}
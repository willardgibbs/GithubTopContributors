package ru.kzavertkin.githubtopcontributors.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import ru.kzavertkin.githubtopcontributors.model.User;
import ru.kzavertkin.githubtopcontributors.service.exception.UserNotFoundException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UserServiceTest {
    @Value("${github.api.users.url}")
    private String userUrlPrefix;

    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private UserService userService;

    @Test
    public void getUserTest() throws UserNotFoundException {
        String ownerName = "someusername";
        User user = new User();
        user.setLogin(ownerName);
        String url = userUrlPrefix + ownerName;
        doReturn(user).when(restTemplate).getForObject(url, User.class);

        assertEquals(user, userService.getUser(ownerName));
    }

    @Test(expected = UserNotFoundException.class)
    public void getUserWithNullUser() throws UserNotFoundException {
        String ownerName = "someusername";
        User user = new User();
        user.setLogin(ownerName);
        String url = userUrlPrefix + ownerName;
        doReturn(null).when(restTemplate).getForObject(url, User.class);

        userService.getUser(ownerName);
    }

    @Test(expected = UserNotFoundException.class)
    public void getUserWithHttpNotFound() throws UserNotFoundException {
        String ownerName = "someusername";
        User user = new User();
        user.setLogin(ownerName);
        String url = userUrlPrefix + ownerName;
        HttpClientErrorException httpClientErrorException = new HttpClientErrorException(HttpStatus.NOT_FOUND);
        doThrow(httpClientErrorException).when(restTemplate).getForObject(url, User.class);

        userService.getUser(ownerName);
    }

    @Test(expected = HttpClientErrorException.class)
    public void getUserWithAnotherHttpStatus() throws UserNotFoundException {
        String ownerName = "someusername";
        User user = new User();
        user.setLogin(ownerName);
        String url = userUrlPrefix + ownerName;
        HttpClientErrorException httpClientErrorException = new HttpClientErrorException(HttpStatus.FORBIDDEN);
        doThrow(httpClientErrorException).when(restTemplate).getForObject(url, User.class);

        userService.getUser(ownerName);
    }
}
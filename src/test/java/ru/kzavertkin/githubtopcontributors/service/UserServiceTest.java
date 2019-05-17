package ru.kzavertkin.githubtopcontributors.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import ru.kzavertkin.githubtopcontributors.model.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
@RunWith(SpringRunner.class)
class UserServiceTest {
    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private UserService userService;

    @Test
    void getUser() {
        String ownerName = "someusername";
        User user = new User();
        user.setLogin(ownerName);
        String url = "https://api.github.com/users/" + ownerName;
        doReturn(user).when(restTemplate).getForObject(url, User.class);

        assertEquals(user, userService.getUser(ownerName));
    }
}
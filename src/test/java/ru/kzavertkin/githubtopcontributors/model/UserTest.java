package ru.kzavertkin.githubtopcontributors.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@JsonTest
public class UserTest {
    @Autowired
    private JacksonTester<User> userJacksonTester;

    @Test
    public void serializationTest() throws IOException {
        User user = getUser();
        assertThat(userJacksonTester.write(user)).isEqualToJson("user.json", User.class);
    }

    @Test
    public void deserializationTest() throws IOException {
        User user = getUser();
        assertThat(userJacksonTester.readObject("user.json")).isEqualTo(user);
    }

    private User getUser() {
        User user = new User();
        user.setLogin("test");
        user.setId(880035535);
        user.setNodeId("test");
        user.setAvatarUrl("test");
        user.setGravatarId("test");
        user.setUrl("test");
        user.setHtmlUrl("test");
        user.setFollowersUrl("test");
        user.setFollowingUrl("test");
        user.setGistsUrl("test");
        user.setStarredUrl("test");
        user.setSubscriptionsUrl("test");
        user.setOrganizationsUrl("test");
        user.setReposUrl("test");
        user.setEventsUrl("test");
        user.setReceivedEventsUrl("test");
        user.setType("User");
        user.setSiteAdmin(false);
        user.setName(null);
        user.setCompany(null);
        user.setBlog("");
        user.setLocation(null);
        user.setEmail(null);
        user.setHireable(false);
        user.setBio(null);
        user.setPublicRepos(9);
        user.setPublicGists(0);
        user.setFollowers(0);
        user.setFollowing(0);
        user.setCreatedAt(new Date(1472310480000L));
        user.setUpdatedAt(new Date(1535382480000L));
        return user;
    }

}
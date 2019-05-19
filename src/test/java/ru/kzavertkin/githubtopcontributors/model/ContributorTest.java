package ru.kzavertkin.githubtopcontributors.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@JsonTest
public class ContributorTest {
    @Autowired
    private JacksonTester<Contributor> contributorJacksonTester;

    @Test
    public void serializationTest() throws IOException {
        Contributor contributor = getContributor();
        assertThat(contributorJacksonTester.write(contributor)).isEqualToJson("contributor.json",
                Contributor.class);
    }

    @Test
    public void deserializationTest() throws IOException {
        Contributor contributor = getContributor();
        assertThat(contributorJacksonTester.readObject("contributor.json")).isEqualTo(contributor);
    }

    private Contributor getContributor() {
        Contributor contributor = new Contributor();
        contributor.setLogin("qwe");
        contributor.setId(1234);
        contributor.setNodeId("qwe");
        contributor.setAvatarUrl("qwe");
        contributor.setGravatarId("qwe");
        contributor.setUrl("qwe");
        contributor.setHtmlUrl("qwe");
        contributor.setFollowersUrl("qwe");
        contributor.setFollowingUrl("qwe");
        contributor.setGistsUrl("qwe");
        contributor.setStarredUrl("qwe");
        contributor.setSubscriptionsUrl("qwe");
        contributor.setOrganizationsUrl("qwe");
        contributor.setReposUrl("qwe");
        contributor.setEventsUrl("qwe");
        contributor.setReceivedEventsUrl("qwe");
        contributor.setType("qwe");
        contributor.setSiteAdmin(false);
        contributor.setContributions(419);
        return contributor;
    }
}
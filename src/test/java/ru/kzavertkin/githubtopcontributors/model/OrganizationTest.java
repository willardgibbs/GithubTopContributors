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
public class OrganizationTest {
    @Autowired
    private JacksonTester<Organization> organizationJacksonTester;

    @Test
    public void serializationTest() throws IOException {
        Organization organization = getOrganization();
        assertThat(organizationJacksonTester.write(organization)).isEqualToJson("organization.json",
                Organization.class);
    }

    @Test
    public void deserializationTest() throws IOException {
        Organization organization = getOrganization();
        assertThat(organizationJacksonTester.readObject("organization.json")).isEqualTo(organization);
    }

    private Organization getOrganization() {
        Organization organization = new Organization();
        organization.setLogin("qwe");
        organization.setId(123123);
        organization.setNodeId("qwe");
        organization.setAvatarUrl("qwe");
        organization.setGravatarId("qwe");
        organization.setUrl("qwe");
        organization.setHtmlUrl("qwe");
        organization.setFollowersUrl("qwe");
        organization.setFollowingUrl("qwe");
        organization.setGistsUrl("qwe");
        organization.setStarredUrl("qwe");
        organization.setSubscriptionsUrl("qwe");
        organization.setOrganizationsUrl("qwe");
        organization.setReposUrl("qwe");
        organization.setEventsUrl("qwe");
        organization.setReceivedEventsUrl("qwe");
        organization.setType("Organization");
        organization.setSiteAdmin(false);
        return organization;
    }

}
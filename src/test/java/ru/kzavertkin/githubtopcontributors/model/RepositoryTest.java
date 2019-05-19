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
public class RepositoryTest {
    @Autowired
    private JacksonTester<Repository> repositoryJacksonTester;

    @Test
    public void serializationTest() throws IOException {
        Repository repository = getRepository();
        assertThat(repositoryJacksonTester.write(repository)).isEqualToJson("repository.json", Repository.class);
    }

    @Test
    public void deserializationTest() throws IOException {
        Repository repository = getRepository();
        assertThat(repositoryJacksonTester.readObject("repository.json")).isEqualTo(repository);
    }

    private Repository getRepository() {
        Repository repository = new Repository();
        repository.setId(12345);
        repository.setNodeId("qwe");
        repository.setName("qwe");
        repository.setFullName("qwe");
        repository.setPrivate(false);
        repository.setOwner(getUser());
        repository.setHtmlUrl("qwe");
        repository.setDescription("qwe");
        repository.setFork(false);
        repository.setUrl("qwe");
        repository.setForksUrl("qwe");
        repository.setKeysUrl("qwe");
        repository.setCollaboratorsUrl("qwe");
        repository.setTeamsUrl("qwe");
        repository.setHooksUrl("qwe");
        repository.setIssueEventsUrl("qwe");
        repository.setEventsUrl("qwe");
        repository.setAssigneesUrl("qwe");
        repository.setBranchesUrl("qwe");
        repository.setTagsUrl("qwe");
        repository.setBlobsUrl("qwe");
        repository.setGitTagsUrl("qwe");
        repository.setGitRefsUrl("qwe");
        repository.setTreesUrl("qwe");
        repository.setStatusesUrl("qwe");
        repository.setLanguagesUrl("qwe");
        repository.setStargazersUrl("qwe");
        repository.setContributorsUrl("qwe");
        repository.setSubscribersUrl("qwe");
        repository.setSubscriptionUrl("qwe");
        repository.setCommitsUrl("qwe");
        repository.setGitCommitsUrl("qwe");
        repository.setCommentsUrl("qwe");
        repository.setIssueCommentUrl("qwe");
        repository.setContentsUrl("qwe");
        repository.setCompareUrl("qwe");
        repository.setMergesUrl("qwe");
        repository.setArchiveUrl("qwe");
        repository.setDownloadsUrl("qwe");
        repository.setIssuesUrl("qwe");
        repository.setPullsUrl("qwe");
        repository.setMilestonesUrl("qwe");
        repository.setNotificationsUrl("qwe");
        repository.setLabelsUrl("qwe");
        repository.setReleasesUrl("qwe");
        repository.setDeploymentsUrl("qwe");
        repository.setCreatedAt(new Date(1472310480000L));
        repository.setUpdatedAt(new Date(1472310480000L));
        repository.setPushedAt(new Date(1472310480000L));
        repository.setGitUrl("qwe");
        repository.setSshUrl("qwe");
        repository.setCloneUrl("qwe");
        repository.setSvnUrl("qwe");
        repository.setHomepage("qwe");
        repository.setSize(117012);
        repository.setStargazersCount(11600);
        repository.setWatchersCount(11600);
        repository.setLanguage("qwe");
        repository.setHasIssues(false);
        repository.setHasProjects(false);
        repository.setHasDownloads(true);
        repository.setHasWiki(false);
        repository.setHasPages(true);
        repository.setForksCount(2701);
        repository.setMirrorUrl(null);
        repository.setArchived(false);
        repository.setDisabled(false);
        repository.setOpenIssuesCount(90);
        repository.setLicense(getLicense());
        repository.setForks(2701);
        repository.setOpenIssues(90);
        repository.setWatchers(11600);
        repository.setDefaultBranch("qwe");
        repository.setOrganization(getOrganization());
        repository.setNetworkCount(2701);
        repository.setSubscribersCount(834);
        return repository;
    }

    private User getUser() {
        User user = new User();
        user.setLogin("qwe");
        user.setId(880035535);
        user.setNodeId("qwe");
        user.setAvatarUrl("qwe");
        user.setGravatarId("qwe");
        user.setUrl("qwe");
        user.setHtmlUrl("qwe");
        user.setFollowersUrl("qwe");
        user.setFollowingUrl("qwe");
        user.setGistsUrl("qwe");
        user.setStarredUrl("qwe");
        user.setSubscriptionsUrl("qwe");
        user.setOrganizationsUrl("qwe");
        user.setReposUrl("qwe");
        user.setEventsUrl("qwe");
        user.setReceivedEventsUrl("qwe");
        user.setType("User");
        user.setSiteAdmin(false);
        return user;
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

    private License getLicense() {
        License license = new License();
        license.setKey("qwe");
        license.setName("qwe");
        license.setSpdxId("qwe");
        license.setUrl("qwe");
        license.setNodeId("qwe");
        return license;
    }

}
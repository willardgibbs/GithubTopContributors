package ru.kzavertkin.githubtopcontributors.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.kzavertkin.githubtopcontributors.model.Contributor;
import ru.kzavertkin.githubtopcontributors.model.Repository;
import ru.kzavertkin.githubtopcontributors.model.User;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
@RunWith(SpringRunner.class)
class TopContributorsServiceTest {
    @MockBean
    private RepositoryService repositoryService;

    @MockBean
    private UserService userService;

    @Autowired
    private TopContributorsService topContributorsService;

    @Test
    void getTopContributors() {
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

        assertEquals(expectedContributorList, topContributorsService.getTopContributors(ownerName, repositoryName, limit));
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
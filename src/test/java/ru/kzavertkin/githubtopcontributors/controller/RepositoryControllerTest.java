package ru.kzavertkin.githubtopcontributors.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.kzavertkin.githubtopcontributors.model.Contributor;
import ru.kzavertkin.githubtopcontributors.service.RepositoryService;
import ru.kzavertkin.githubtopcontributors.service.exception.ContributorNotFoundException;
import ru.kzavertkin.githubtopcontributors.service.exception.RepositoryNotFoundException;
import ru.kzavertkin.githubtopcontributors.service.exception.UserNotFoundException;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RepositoryControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private RepositoryService repositoryService;

    @Test
    public void getTop3Contributors() throws Exception {
        String ownerName = "someusername";
        String repositoryName = "somerep";
        int limit = 3;

        List<Contributor> contributorList = getContributorList();

        doReturn(contributorList).when(repositoryService).getTopContributors(ownerName, repositoryName, limit);

        mvc.perform(get("/githubapiclient/repos/" + ownerName + "/" + repositoryName + "/contributors/top3")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].login", is("2")))
                .andExpect(jsonPath("$[0].contributions", is(2)))
                .andExpect(jsonPath("$[0].id", is(12)));
    }

    @Test
    public void getTop3ContributorsWithUserNotFound() throws Exception {
        String ownerName = "someusername";
        String repositoryName = "somerep";
        int limit = 3;

        doThrow(new UserNotFoundException()).when(repositoryService).getTopContributors(ownerName, repositoryName,
                limit);

        mvc.perform(get("/githubapiclient/repos/" + ownerName + "/" + repositoryName + "/contributors/top3")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(status().reason("User " + ownerName + " is not found"));
    }

    @Test
    public void getTop3ContributorsWithRepositoryNotFound() throws Exception {
        String ownerName = "someusername";
        String repositoryName = "somerep";
        int limit = 3;

        doThrow(new RepositoryNotFoundException()).when(repositoryService).getTopContributors(ownerName, repositoryName,
                limit);

        mvc.perform(get("/githubapiclient/repos/" + ownerName + "/" + repositoryName + "/contributors/top3")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(status().reason("Repository " + repositoryName + " is not found"));
    }

    @Test
    public void getTop3ContributorsWithContributorNotFound() throws Exception {
        String ownerName = "someusername";
        String repositoryName = "somerep";
        int limit = 3;

        doThrow(new ContributorNotFoundException()).when(repositoryService).getTopContributors(ownerName,
                repositoryName, limit);

        mvc.perform(get("/githubapiclient/repos/" + ownerName + "/" + repositoryName + "/contributors/top3")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(status().reason("Contributors is not found"));
    }

    private List<Contributor> getContributorList() {
        List<Contributor> contributorList = new ArrayList<>();

        Contributor contributor2 = new Contributor();
        contributor2.setLogin("2");
        contributor2.setContributions(2);
        contributor2.setId(12);
        contributorList.add(contributor2);

        return contributorList;
    }
}
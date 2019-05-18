package ru.kzavertkin.githubtopcontributors.controller;

import org.junit.jupiter.api.Test;
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

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class RepositoryControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private RepositoryService repositoryService;

    @Test
    void getTop3Contributors() throws Exception {
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
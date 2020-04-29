package ru.kzavertkin.githubtopcontributors.controller

import com.github.tomakehurst.wiremock.client.WireMock
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import org.springframework.core.io.ClassPathResource
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

import static com.github.tomakehurst.wiremock.client.WireMock.equalTo
import static org.hamcrest.Matchers.hasSize
import static org.hamcrest.Matchers.is
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static wiremock.org.apache.commons.io.FileUtils.readFileToString

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock
class RepositoryControllerTest {

    @Autowired
    WebApplicationContext context

    MockMvc mockMvc

    @Before
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build()
    }

    @Test
    void "success getting top contributes"() {
        def ownerName = "someusername"
        def repositoryName = "somerep"
        def limit = 3
        def order = "desc"
        WireMock.stubFor(WireMock.get(WireMock.urlPathEqualTo("/users/${ownerName}"))
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(readFileToString(new ClassPathResource("response/get_user_by_login.json").getFile()))))
        WireMock.stubFor(WireMock.get(WireMock.urlPathEqualTo("/repos/${ownerName}/${repositoryName}"))
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(readFileToString(new ClassPathResource("response/get_repository_by_owner_and_name.json").getFile()))))
        WireMock.stubFor(WireMock.get(WireMock.urlPathEqualTo("/repos/${ownerName}/${repositoryName}/contributors"))
                .withQueryParam("query", equalTo("contributions"))
                .withQueryParam("order", equalTo("desc"))
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(readFileToString(new ClassPathResource("response/get_contributors_in_repository.json").getFile()))))
        mockMvc.perform(
                MockMvcRequestBuilders.get("/repos/${ownerName}/${repositoryName}/contributors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .queryParam("limit", limit.toString())
                        .queryParam("order", order))
                .andExpect(status().isOk())
                .andExpect(jsonPath('$', hasSize(limit)))
                .andExpect(jsonPath('$[0].login', is("someusername")))
                .andExpect(jsonPath('$[0].contributions', is(3)))
                .andExpect(jsonPath('$[0].id', is(212797231)))
                .andExpect(jsonPath('$[1].login', is("someusername1")))
                .andExpect(jsonPath('$[1].contributions', is(2)))
                .andExpect(jsonPath('$[1].id', is(212797232)))
                .andExpect(jsonPath('$[2].login', is("someusername2")))
                .andExpect(jsonPath('$[2].contributions', is(1)))
                .andExpect(jsonPath('$[2].id', is(212797233)))
    }
}
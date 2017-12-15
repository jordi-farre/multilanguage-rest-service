package multilanguage;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.RequestMatcher;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.context.WebApplicationContext;

import javax.sql.DataSource;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.converter.json.Jackson2ObjectMapperBuilder.json;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class RemoteCallFeature {

    @Autowired
    private WebApplicationContext context;
    @Autowired
    private DataSource dataSource;
    private MockMvc mockMvc;
    private AsyncRestTemplate asyncRestTemplate = new AsyncRestTemplate();
    private MockRestServiceServer mockRestServiceServer;

    @Before
    public void initialize() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
        this.mockRestServiceServer = MockRestServiceServer.createServer(asyncRestTemplate);
    }

    @Test
    public void should_make_a_remote_call_and_store_result_in_database() throws Exception {
        setupRemoteCallBehavior();
        RequestBuilder requestBuilder = createServerRequest();

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json(this.getJsonFrom("serverResponse.json")));

        mockRestServiceServer.verify();
        assertRemoteCallRegisteredInDatabase();
    }

    private RequestBuilder createServerRequest() throws URISyntaxException, IOException {
        return MockMvcRequestBuilders.post("/remote/call")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(this.getJsonFrom("serverRequest.json"));
    }

    private void setupRemoteCallBehavior() throws URISyntaxException, IOException {
        this.mockRestServiceServer.expect(requestTo("http://test/service"))
                .andExpect(method(POST))
                .andRespond(withSuccess(this.getJsonFrom("remoteCallResponse.json"), MediaType.APPLICATION_JSON));
    }

    private void assertRemoteCallRegisteredInDatabase() throws SQLException {
        try (Connection connection = this.dataSource.getConnection(); Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery("SELECT ID, METHOD, ORIGIN, DATA FROM REMOTE_CALL")) {
                resultSet.next();
                assertThat(resultSet.getString("ID"), is("067e6162-3b6f-4ae2-a171-2470b63dff00"));
                assertThat(resultSet.getString("METHOD"), is("POST"));
                assertThat(resultSet.getString("ORIGIN"), is("83.57.227.154"));
                assertThat(resultSet.getString("DATA"), is("hello"));
            }
        }
    }

    private String getJsonFrom(String fileName) throws URISyntaxException, IOException {
        return new String(Files.readAllBytes(Paths.get(this.getClass().getClassLoader().getResource(fileName).toURI())), StandardCharsets.UTF_8);
    }


}

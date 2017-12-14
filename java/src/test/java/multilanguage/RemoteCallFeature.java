package multilanguage;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.RequestMatcher;
import org.springframework.web.client.AsyncRestTemplate;

import static org.springframework.http.HttpMethod.POST;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

public class RemoteCallFeature {


    private AsyncRestTemplate asyncRestTemplate;

    @Test
    public void should_make_a_remote_call_and_store_result_in_database() {
        MockRestServiceServer server = MockRestServiceServer.createServer(asyncRestTemplate);
        server.expect(requestTo("http://test/service")).andExpect(method(POST)).andRespond(withSuccess("{\n" +
                "  \"json\": {\n" +
                "    \"request\": \"hello\"\n" +
                "  },\n" +
                "  \"method\": \"POST\",\n" +
                "  \"origin\": \"83.57.227.154\"\n" +
                "}", MediaType.APPLICATION_JSON));

        server.verify();
    }
    
    
}

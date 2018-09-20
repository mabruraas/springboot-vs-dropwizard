package no.mab.tech.resource;

import com.google.gson.Gson;
import no.mab.tech.model.Person;
import no.mab.tech.model.User;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import java.io.IOException;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/")
@Produces(APPLICATION_JSON)
public class RestApiConsumerResource {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String restApiUrl;
    private HttpClient httpClient;

    // Is it possible to access configuration without passing its content in the constructor?
    public RestApiConsumerResource(String restApiUrl, HttpClient httpClient) {
        this.restApiUrl = restApiUrl;
        this.httpClient = httpClient;
    }

    @GET
    @Path("/persons")
    public List<Person> getPersons() throws IOException {
        logger.info("Fetching random person");

        HttpResponse response = httpClient.execute(new HttpGet(restApiUrl));
        String json = IOUtils.toString(response.getEntity().getContent());

        Gson gson = new Gson();
        User user = gson.fromJson(json, User.class);

        return user.getPersonList();
    }
}

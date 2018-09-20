package no.mab.tech.controller;

import no.mab.tech.model.Person;
import no.mab.tech.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class RestApiConsumerController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${external.api.url}")
    private String restApiUrl;

    @RequestMapping(value = "/persons", method = RequestMethod.GET)
    public List<Person> getPersons() {
        logger.info("Fetching random person");

        RestTemplate rt = new RestTemplate();
        User user = rt.getForObject(restApiUrl, User.class);

        return user.getPersonList();
    }
}

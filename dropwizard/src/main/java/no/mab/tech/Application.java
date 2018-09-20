package no.mab.tech;

import io.dropwizard.client.HttpClientBuilder;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import no.mab.tech.resource.RestApiConsumerResource;
import org.apache.http.impl.client.CloseableHttpClient;

public class Application extends io.dropwizard.Application<Configuration> {

    public static void main(String[] args) throws Exception {
        new Application().run(args);
    }

    @Override
    public void initialize(Bootstrap<Configuration> bootstrap) {
        bootstrap.setConfigurationSourceProvider(
                new SubstitutingSourceProvider(
                        bootstrap.getConfigurationSourceProvider(),
                        new EnvironmentVariableSubstitutor(false)
                )
        );
    }

    @Override
    public void run(Configuration configuration, Environment environment) throws Exception {

        // Initialization of HttpClient, to be accessible through the registered resources
        CloseableHttpClient httpClient = new HttpClientBuilder(environment)
                .using(configuration.getHttpClientConfiguration())
                .build("DropWizard Test");

        // Registration of each resource, should possibly be switched with usage of Google Guice
        final RestApiConsumerResource resource = new RestApiConsumerResource(
                configuration.getRestApiUrl(), httpClient
        );

        environment.jersey().register(resource);
    }
}

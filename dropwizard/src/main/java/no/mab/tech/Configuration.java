package no.mab.tech;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.client.HttpClientConfiguration;

public class Configuration extends io.dropwizard.Configuration {

    // Property should be changed from the "flat" format to actual hierarchy
    @JsonProperty("external.api.url")
    private String restApiUrl;

    // Is it possible to do this with Google Guice instead of calling new?
    private HttpClientConfiguration httpClientConfiguration = new HttpClientConfiguration();

    public String getRestApiUrl() {
        return restApiUrl;
    }

    public HttpClientConfiguration getHttpClientConfiguration() {
        return httpClientConfiguration;
    }
}

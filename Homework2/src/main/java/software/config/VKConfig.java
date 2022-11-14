package software.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;
import software.utils.VKProxy;

import java.net.http.HttpClient;

@Configuration
public class VKConfig {

    @Bean
    @ConfigurationProperties(prefix = "vk")
    public VKProperties createVKProperties() {
        return new VKProperties();
    }

    @Bean
    public UriBuilderFactory createUriBuilderFactory(VKProperties properties) {
        return new DefaultUriBuilderFactory(
                UriComponentsBuilder
                        .newInstance()
                        .scheme(properties.getSchema())
                        .host(properties.getHost())
                        .path(properties.getBasePath())
        );
    }

    @Bean
    public VKProxy createVKProxy(UriBuilderFactory factory) {
        HttpClient httpClient = HttpClient.newBuilder().build();
        ObjectMapper mapper = new ObjectMapper();
        return new VKProxy(factory, httpClient, mapper);
    }
}

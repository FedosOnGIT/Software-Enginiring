package software.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.util.UriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;
import software.request.vk.VKRequest;
import software.response.vk.VKResponse;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@RequiredArgsConstructor
public class VKProxy {
    static class ProxyException extends RuntimeException {
        public ProxyException(String message) {
            super(message);
        }
    }
    private final UriBuilderFactory factory;
    private final HttpClient client;
    private final ObjectMapper mapper;

    public <T> T askVK(String path, VKRequest vkRequest, TypeReference<VKResponse<T>> type) {
        try {
            URI uri = vkRequest
                    .addQueries(UriComponentsBuilder
                            .fromUri(factory
                                    .builder()
                                    .path(path)
                                    .build()));

            HttpRequest request = HttpRequest
                    .newBuilder()
                    .uri(uri)
                    .POST(HttpRequest.BodyPublishers.noBody())
                    .version(HttpClient.Version.HTTP_1_1)
                    .build();

            HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());


            if (response.statusCode() != HttpStatus.OK.value()) {
                throw new ProxyException("Request at %1$s, got response with status code is %2$s".formatted(path, response.statusCode()));
            }

            if (response.body() == null) {
                throw new ProxyException("Request at %1$s, got null body".formatted(path));
            }

            VKResponse<T> vkResponse = mapper.readValue(response.body(), type);

            if (vkResponse.getResponse() == null) {
                throw new ProxyException("Request at %1$s, got null response".formatted(path));
            }
            return vkResponse.getResponse();
        } catch (IOException | InterruptedException e) {
            throw new ProxyException("Can't send request %1$s".formatted(e.getMessage()));
        }
    }
}

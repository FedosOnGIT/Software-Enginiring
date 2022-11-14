package software.request.vk;

import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

public interface VKRequest {
    URI addQueries(UriComponentsBuilder builder);
}

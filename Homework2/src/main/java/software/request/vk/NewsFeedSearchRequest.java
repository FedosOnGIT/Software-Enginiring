package software.request.vk;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewsFeedSearchRequest implements VKRequest {
    private String access_token;
    private String query;
    private Integer count;
    private Long start_time;
    private Long end_time;
    private String version;

    @Override
    public URI addQueries(UriComponentsBuilder builder) {
        return builder
                .queryParam("access_token", access_token)
                .queryParam("v", version)
                .queryParam("q", query)
                .queryParam("count", count)
                .queryParam("start_time", start_time)
                .queryParam("end_time", end_time)
                .build()
                .toUri();
    }
}

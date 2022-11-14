package software.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.config.VKProperties;
import software.request.SearchRequest;
import software.request.vk.NewsFeedSearchRequest;
import software.response.SearchResponse;
import software.response.vk.NewsFeedSearchResponse;
import software.response.vk.VKResponse;
import software.service.NewsFeedService;
import software.utils.DateConverter;
import software.utils.VKProxy;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsFeedServiceImpl implements NewsFeedService {
    private final VKProperties properties;
    private final VKProxy proxy;
    @Override
    public SearchResponse searchNews(SearchRequest request) {
        int hours = request.getHours();
        List<Long> timestamps = DateConverter.convertTime(hours);
        List<Integer> postsPerHour = new ArrayList<>();

        for (int i = 0; i < hours; i++) {
            Long startTime = timestamps.get(i);
            Long endTime = timestamps.get(i + 1);
            Integer DEFAULT_COUNT = 200;
            NewsFeedSearchRequest vkRequest = NewsFeedSearchRequest.builder()
                    .access_token(properties.getToken())
                    .query(request.getQuery())
                    .start_time(startTime)
                    .end_time(endTime)
                    .count(DEFAULT_COUNT)
                    .version(properties.getVersion())
                    .build();
            var type = new TypeReference<VKResponse<NewsFeedSearchResponse>>() {
            };

            NewsFeedSearchResponse response = proxy.askVK(properties.getNewsFeed().getSearch(), vkRequest, type);
            postsPerHour.add(response.getCount());
        }
        return SearchResponse
                .builder()
                .postsPerHour(postsPerHour)
                .build();
    }
}

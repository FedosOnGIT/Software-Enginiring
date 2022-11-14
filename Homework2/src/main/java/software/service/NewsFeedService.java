package software.service;

import software.request.SearchRequest;
import software.response.SearchResponse;

public interface NewsFeedService {
    SearchResponse searchNews(SearchRequest request);
}

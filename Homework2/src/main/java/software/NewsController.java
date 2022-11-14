package software;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import software.request.SearchRequest;
import software.response.SearchResponse;
import software.service.NewsFeedService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class NewsController {
    private final NewsFeedService newsFeed;

    @GetMapping("/news")
    public ResponseEntity<SearchResponse> getNews(
            @Valid @RequestBody SearchRequest request
    ) {
        return ResponseEntity.ok(newsFeed.searchNews(request));
    }
}

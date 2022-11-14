package software.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VKProperties {
    private Integer connectionTimeout;
    private Integer socketTimeout;
    private String schema;
    private String host;
    private String basePath;
    private String token;
    private String version;

    private NewsFeed newsFeed;

    @Getter
    @Setter
    public static class NewsFeed {
        private String search;
    }
}

package software;

import lombok.SneakyThrows;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class VKApplicationTest {
    @Autowired
    private MockMvc mvc;
    @SpyBean
    private UriBuilderFactory factory;
    private static MockWebServer webServer;

    private static Dispatcher createDispatcher() {
        return new Dispatcher() {

            int number = 0;

            @NotNull
            @Override
            public MockResponse dispatch(@NotNull RecordedRequest request) {
                assert request.getPath() != null;
                if (request.getPath().startsWith("/newsfeed.search")) {
                    return new MockResponse()
                            .setResponseCode(200)
                            .addHeader("Content-Type", "application/json")
                            .setBody("""
                                    {
                                        "response" :
                                        {
                                            "count": %1$s,
                                            "total_count" : %1$s
                                        }
                                    }
                                    """.formatted(number++));
                } else {
                    return new MockResponse().setResponseCode(404);
                }
            }
        };
    }

    @BeforeAll
    public static void init() throws IOException {
        webServer = new MockWebServer();
        webServer.start();
    }

    @BeforeEach
    public void initialize() {
        var url = webServer.url("/");
        when(factory.builder())
                .thenAnswer((inv) -> new DefaultUriBuilderFactory(UriComponentsBuilder
                        .newInstance()
                        .scheme(url.scheme())
                        .host(url.host())
                        .port(url.port()).path(""))
                        .builder());
    }

    @AfterAll
    static void tearDown() throws IOException {
        webServer.shutdown();
    }

    @Test
    @SneakyThrows
    public void ControllerTest() {
        webServer.setDispatcher(createDispatcher());
        mvc.perform(
                        MockMvcRequestBuilders.get("/news")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                            "query" : "test",
                                            "hours" : 5
                                        }
                                        """)
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.postsPerHour", hasSize(5)))
                .andExpect(jsonPath("$.postsPerHour[0]", is(0)))
                .andExpect(jsonPath("$.postsPerHour[4]", is(4)));
    }
}

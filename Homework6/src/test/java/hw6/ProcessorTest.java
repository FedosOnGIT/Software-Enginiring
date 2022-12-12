package hw6;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ProcessorTest {
    @Autowired
    private MockMvc mvc;

    @SneakyThrows
    private void scenario(String expression, String expectedExpression, Integer expectedResult) {
        mvc.perform(
                        post("/v0/process")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                            "expression" : "%s"
                                        }
                                        """.formatted(expression))
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.expression", is(expectedExpression)))
                .andExpect(jsonPath("$.result", is(expectedResult)));
    }

    @Test
    public void deadInsideTest() {
        scenario("1000 - 7", "1000 7 - ", 993);
    }

    @Test
    public void subtractTest() {
        scenario("3 - 2 -1", "3 2 - 1 - ", 0);
    }

    @Test
    public void priorityTest() {
        scenario("2 + 2 * 2", "2 2 2 * + ", 6);
    }

    @Test
    public void bracketTest() {
        scenario("(((2 + 2))) / ((2))", "2 2 + 2 / ", 2);
    }

    @Test
    @SneakyThrows
    public void errorTest() {
        mvc.perform(
                        post("/v0/process")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                            "expression" : "(2 + 2"
                                        }
                                        """)
                ).andDo(print())
                .andExpect(status().isBadRequest());
    }
}

package mvc.controller;

import lombok.SneakyThrows;
import mvc.dao.Database;
import mvc.model.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TaskControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private Database database;

    @Test
    @SneakyThrows
    public void getTest() {
        String name = "name";
        String task = "task";
        when(database.get(any()))
                .thenReturn(List
                        .of(Task
                                .builder()
                                .name(task)
                                .build()));

        mvc.perform(
                        get("/task-list?name=name")
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.list.name", is(name)))
                .andExpect(jsonPath("$.list.tasks", hasSize(1)))
                .andExpect(jsonPath("$.list.tasks[0].name", is(task)));

        verify(database, times(1)).get(eq(name));
    }

    @Test
    @SneakyThrows
    public void addTest() {
        String name = "name";
        mvc.perform(
                        post("/new/list")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                            "listName" : "name"
                                        }
                                        """)
                ).andDo(print())
                .andExpect(status().isOk());

        verify(database, times(1)).add(eq(name));
    }

    @Test
    @SneakyThrows
    public void addTask() {
        String name = "name";
        String task = "task";
        String description = "description";
        mvc.perform(
                        post("/new/task")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                            "listName" : "name",
                                            "name" : "task",
                                            "description" : "description"
                                        }
                                        """)
                ).andDo(print())
                .andExpect(status().isOk());

        verify(database, times(1)).add(eq(name), eq(task), eq(description));
    }

    @Test
    @SneakyThrows
    public void deleteList() {
        String name = "name";
        String task = "task";

        when(database.delete(any()))
                .thenReturn(List.of(Task
                        .builder()
                        .name(task)
                        .done(true)
                        .build()));

        mvc.perform(
                        delete("/delete?name=name")
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.list.name", is(name)))
                .andExpect(jsonPath("$.list.tasks", hasSize(1)))
                .andExpect(jsonPath("$.list.tasks[0].name", is(task)))
                .andExpect(jsonPath("$.list.tasks[0].done", is(true)));

        verify(database, times(1)).delete(eq(name));
    }
}

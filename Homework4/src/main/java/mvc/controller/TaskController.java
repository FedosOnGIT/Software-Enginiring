package mvc.controller;

import lombok.RequiredArgsConstructor;
import mvc.dao.Database;
import mvc.model.Task;
import mvc.model.TaskList;
import mvc.requests.AddListRequest;
import mvc.requests.AddTaskRequest;
import mvc.requests.DoTaskRequest;
import mvc.responses.AllTasksResponse;
import mvc.responses.TaskListResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.function.Function;

@RestController
@RequiredArgsConstructor
public class TaskController {
    private final Database database;

    private ResponseEntity<TaskListResponse> taskReturning(String name, Function<String, List<Task>> processing) {
        var tasks = processing.apply(name);
        return ResponseEntity.ok(TaskListResponse
                .builder()
                .list(TaskList
                        .builder()
                        .name(name)
                        .tasks(tasks)
                        .build())
                .build());
    }

    @GetMapping("/task-list")
    public ResponseEntity<TaskListResponse> getTasks(String name) {
        return taskReturning(name, database::get);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<TaskListResponse> deleteList(String name) {
        return taskReturning(name, database::delete);
    }

    @PostMapping("/new/list")
    public ResponseEntity<TaskListResponse> newList(@Valid @RequestBody AddListRequest request) {
        database.add(request.getListName());
        return getTasks(request.getListName());
    }

    @PostMapping("/new/task")
    public ResponseEntity<TaskListResponse> newTask(@Valid @RequestBody AddTaskRequest request) {
        database.add(request.getListName(), request.getName(), request.getDescription());
        return getTasks(request.getListName());
    }

    @PostMapping("/do")
    public ResponseEntity<TaskListResponse> doTask(@Valid @RequestBody DoTaskRequest request) {
        database.process(request.getListName(), request.getName());
        return getTasks(request.getListName());
    }

    @GetMapping("/task-list/all")
    public ResponseEntity<AllTasksResponse> getAll() {
        var all = database.getAll();
        List<TaskList> lists = all
                .entrySet()
                .stream()
                .map(entry -> TaskList
                        .builder()
                        .name(entry.getKey())
                        .tasks(entry.getValue())
                        .build())
                .toList();
        return ResponseEntity.ok(AllTasksResponse
                .builder()
                .lists(lists)
                .build());
    }
}

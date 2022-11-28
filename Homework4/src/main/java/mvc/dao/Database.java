package mvc.dao;

import mvc.model.Task;

import java.util.List;
import java.util.Map;

public interface Database {
    void add(String name);

    void add(String name, String task, String description);

    void process(String name, String task);

    List<Task> delete(String name);

    List<Task> get(String name);

    Map<String, List<Task>> getAll();
}

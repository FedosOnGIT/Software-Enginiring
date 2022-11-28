package mvc.dao;

import lombok.RequiredArgsConstructor;
import mvc.model.Task;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class MemoryDatabase implements Database {
    private final Map<String, Map<String, Task>> lists;

    @Override
    public void add(String name) {
        lists.put(name, new HashMap<>());
    }

    @Override
    public void add(String name, String task, String description) {
        if (!lists.containsKey(name)) {
            add(name);
        }
        lists.get(name).put(task, Task
                .builder()
                .name(task)
                .description(description)
                .build());
    }

    @Override
    public List<Task> delete(String name) {
        if (!lists.containsKey(name)) {
            throw new IllegalArgumentException("No such list as %1$s".formatted(name));
        }
        return lists.remove(name).values().stream().toList();
    }

    @Override
    public void process(String name, String task) {
        if (!lists.containsKey(name) || !lists.get(name).containsKey(task)) {
            throw new IllegalArgumentException("No task with taskList %1$s and name %2$s found".formatted(name, task));
        }
        Task done = lists.get(name).get(task);
        done.setDone(true);
        done.setChanged(ZonedDateTime.now());
    }

    @Override
    public List<Task> get(String name) {
        var tasks = lists.get(name);
        return tasks == null
                ? Collections.emptyList()
                : tasks.values().stream().toList();
    }


    @Override
    public Map<String, List<Task>> getAll() {
        var all = new HashMap<String, List<Task>>();
        for (var list : lists.entrySet()) {
            all.put(list.getKey(), list
                    .getValue()
                    .values()
                    .stream()
                    .toList());
        }
        return all;
    }
}

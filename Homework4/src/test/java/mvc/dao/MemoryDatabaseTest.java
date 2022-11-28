package mvc.dao;

import mvc.model.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MemoryDatabaseTest {
    @Autowired
    Database database;

    @Test
    public void addAndDoTest() {
        database.add("First");

        database.add("First", "Тасочка1", "Описание1");
        List<Task> firstList = database.get("First");
        assertEquals(1, firstList.size());
        assertEquals("Тасочка1", firstList.get(0).getName());
        assertEquals("Описание1", firstList.get(0).getDescription());
        assertFalse(firstList.get(0).getDone());
        assertNotNull(firstList.get(0).getCreated());
        assertNotNull(firstList.get(0).getChanged());

        database.process("First", "Тасочка1");
        firstList = database.get("First");
        assertTrue(firstList.get(0).getDone());
    }

    @Test
    public void addingTest() {
        database.add("1");
        database.add("1", "1", null);
        database.add("1", "2", null);

        database.add("2");
        database.add("2", "1", null);

        Map<String, List<Task>> all = database.getAll();

        assertEquals(2, all.size());
        assertEquals(2, all.get("1").size());
        assertEquals(1, all.get("2").size());

        database.delete("1");

        all = database.getAll();
        assertEquals(1, all.size());
        assertEquals(1, all.get("2").size());
    }
}

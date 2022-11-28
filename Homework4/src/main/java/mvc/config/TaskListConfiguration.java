package mvc.config;

import mvc.dao.Database;
import mvc.dao.MemoryDatabase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class TaskListConfiguration {
    @Bean
    public Database createDatabase() {
        return new MemoryDatabase(new HashMap<>());
    }
}

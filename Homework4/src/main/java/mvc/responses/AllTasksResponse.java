package mvc.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mvc.model.TaskList;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AllTasksResponse {
    @Schema(title = "Все списки заданий")
    private List<TaskList> lists;
}

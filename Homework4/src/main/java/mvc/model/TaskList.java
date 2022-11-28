package mvc.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(title = "Список задач")
public class TaskList {
    @NotEmpty
    @Schema(title = "Имя списка")
    private String name;

    @NotNull
    @Builder.Default
    @Schema(title = "Список задач")
    private List<Task> tasks = new ArrayList<>();
}

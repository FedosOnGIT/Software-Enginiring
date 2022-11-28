package mvc.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddTaskRequest {
    @NotEmpty
    @Schema(title = "Имя списка")
    private String listName;

    @NotEmpty
    @Schema(title = "Имя задачи")
    private String name;

    @Schema(title = "Описание задачи", nullable = true)
    private String description;
}

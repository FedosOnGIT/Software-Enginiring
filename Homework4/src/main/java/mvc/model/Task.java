package mvc.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "Задача")
public class Task {
    @NotEmpty
    @Schema(title = "Имя задачи")
    private String name;

    @NotNull
    @Schema(title = "Статус исполнения задачи")
    @Builder.Default
    private Boolean done = false;

    @NotNull
    @Schema(title = "Описание задачи", nullable = true)
    private String description;

    @NotNull
    @Schema(title = "Время создания")
    @Builder.Default
    private ZonedDateTime created = ZonedDateTime.now();

    @NotNull
    @Schema(title = "Время изменения")
    @Builder.Default
    private ZonedDateTime changed = ZonedDateTime.now();
}

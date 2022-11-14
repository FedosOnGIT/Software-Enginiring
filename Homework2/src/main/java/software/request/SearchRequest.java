package software.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotEmpty;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequest {
    @NotEmpty
    @Schema(title = "query", description = "Хештег, по которому будет идти поиск")
    private String query;

    @NonNull
    @Schema(title = "hours", description = "Время за которое надо построить график")
    private Integer hours;
}

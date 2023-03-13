package numble.instagramprecourse.repository.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardDto {

    @NotNull
    private String title;

    @NotNull
    private String content;
}

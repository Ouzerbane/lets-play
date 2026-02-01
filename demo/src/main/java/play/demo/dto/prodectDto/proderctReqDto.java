package play.demo.dto.prodectDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class proderctReqDto {

    @NotBlank(message = "name should be not empty")
    @Size(min = 3, max = 30, message = "name should be between 3 and 30")
    private String name;

    @NotBlank(message = "description should be not empty")
    @Size(min = 6, max = 50, message = "sescription should be between 6 and 50")
    private String description;

    @NotNull(message = "Price is required")
    @PositiveOrZero(message = "Price must be greater than or equal to 0")
    private Double price;
}

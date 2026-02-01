package play.demo.dto.prodectDto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class proderctEditReqDto {

    @NotBlank(message = "name should be not empty")
    String id;

    private String name;
    private String description;
    private Double price;
}

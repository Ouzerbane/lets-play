package play.demo.dto.prodectDto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class proderctResDto {
    private String id;
    private String name;
    private String description;
    private Double price;
    private String userId;
    // private String username ;
}

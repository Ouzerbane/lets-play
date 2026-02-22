package play.demo.dto.UserDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class updatUserToAdminDto {
    private String id;
    private String username;
    private String email;
    private String role;
}

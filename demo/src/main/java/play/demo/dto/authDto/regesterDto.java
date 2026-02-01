package play.demo.dto.authDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class regesterDto {
    @NotBlank(message = "Username is required")
    @Size(min = 3 , max = 30 , message = "Username must be between 3 and 30 characters")
    private String username;
    @NotBlank(message = "Email is required")
    @Email(message = "Email format is invalid")
    private String email ;
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password ;
}

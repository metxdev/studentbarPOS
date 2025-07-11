package ee.metxdev.tudengibaar.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDTO {
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private Long roleId;
}

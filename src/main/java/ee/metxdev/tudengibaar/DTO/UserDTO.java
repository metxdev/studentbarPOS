package ee.metxdev.tudengibaar.DTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long userId;
    private String email;
    private String firstName;
    private String lastName;
    private String roleName;
}

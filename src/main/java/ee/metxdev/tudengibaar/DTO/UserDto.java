package ee.metxdev.tudengibaar.DTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long userId;
    private String email;
    private String firstName;
    private String lastName;
    private String roleName; // Only expose the name of the role
}

package ee.metxdev.tudengibaar.security.dto;

import ee.metxdev.tudengibaar.DTO.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private UserDTO userDTO;
}

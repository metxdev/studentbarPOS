package ee.metxdev.tudengibaar.mappers;

import ee.metxdev.tudengibaar.DTO.CreateUserDTO;
import ee.metxdev.tudengibaar.DTO.UserDTO;
import ee.metxdev.tudengibaar.entity.User;

public class UserMapper {

    public static UserDTO toDTO(User user) {
        return new UserDTO(
                user.getUserId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getRole() != null ? user.getRole().getName() : null
        );
    }

    public static User toEntity(CreateUserDTO dto) {
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPassword(dto.getPassword()); // plain password here
        return user;
    }
}

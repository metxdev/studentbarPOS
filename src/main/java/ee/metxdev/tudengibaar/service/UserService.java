package ee.metxdev.tudengibaar.service;

import ee.metxdev.tudengibaar.DTO.CreateUserDTO;
import ee.metxdev.tudengibaar.DTO.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserDTO createUser(CreateUserDTO dto);
    List<UserDTO> getAllUsers();
    Optional<UserDTO> getUserById(Long id);
    void deleteUser(Long id);
}
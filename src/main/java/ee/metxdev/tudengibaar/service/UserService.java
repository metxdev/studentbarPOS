package ee.metxdev.tudengibaar.service;

import ee.metxdev.tudengibaar.DTO.UserDto;
import ee.metxdev.tudengibaar.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserDto createUser(User user);
    List<UserDto> getAllUsers();
    Optional<UserDto> getUserById(Long id);
    void deleteUser(Long id);
}
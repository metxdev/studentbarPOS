package ee.metxdev.tudengibaar.service;

import ee.metxdev.tudengibaar.DTO.UserDto;
import ee.metxdev.tudengibaar.entity.Role;
import ee.metxdev.tudengibaar.entity.User;
import ee.metxdev.tudengibaar.repository.RoleRepository;
import ee.metxdev.tudengibaar.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImp implements UserService {

    private final UserRepository userRepo;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public UserServiceImp(UserRepository userRepo, RoleRepository roleRepository, UserRepository userRepository) {
        this.userRepo = userRepo;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public UserDto createUser(User user) {
        if (user.getRole() == null || user.getRole().getRoleId() == null) {
            throw new IllegalArgumentException("Role must be provided with valid roleId.");
        }
        Role role = roleRepository.findById(user.getRole().getRoleId())
                .orElseThrow(() -> new IllegalArgumentException("Role not found." + user.getRole().getRoleId()));

        user.setRole(role);
        User saved = userRepository.save(user);
        return toDTO(saved);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepo.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserDto> getUserById(Long id) {
        return userRepo.findById(id)
                .map(this::toDTO);
    }

    @Override
    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }

    private UserDto toDTO(User user) {
        return new UserDto(
                user.getUserId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getRole() != null ? user.getRole().getName() : null
        );
    }
}
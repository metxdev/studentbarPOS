package ee.metxdev.tudengibaar.service;

import ee.metxdev.tudengibaar.DTO.CreateUserDTO;
import ee.metxdev.tudengibaar.DTO.UserDTO;
import ee.metxdev.tudengibaar.entity.Role;
import ee.metxdev.tudengibaar.entity.User;
import ee.metxdev.tudengibaar.mappers.UserMapper;
import ee.metxdev.tudengibaar.repository.RoleRepository;
import ee.metxdev.tudengibaar.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UserServiceImp implements UserService {

    private final UserRepository userRepo;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImp(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDTO createUser(CreateUserDTO dto) {
        if (dto.getRoleId() == null) {
            throw new IllegalArgumentException("Role must be provided with valid roleId.");
        }

        Role role = roleRepository.findById(dto.getRoleId())
                .orElseThrow(() -> new IllegalArgumentException("Role not found: " + dto.getRoleId()));

        User user = UserMapper.toEntity(dto);
        user.setRole(role);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = userRepository.save(user);

        return UserMapper.toDTO(savedUser);
    }


    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserDTO> getUserById(Long id) {
        return userRepository.findById(id)
                .map(UserMapper::toDTO);
    }

    @Override
    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }

}
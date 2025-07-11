package ee.metxdev.tudengibaar.controller;

import ee.metxdev.tudengibaar.DTO.CreateUserDTO;
import ee.metxdev.tudengibaar.DTO.UserDTO;
import ee.metxdev.tudengibaar.entity.User;
import ee.metxdev.tudengibaar.service.RoleService;
import ee.metxdev.tudengibaar.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody @Valid CreateUserDTO user) {
        UserDTO savedUsed = userService.createUser(user);
        return ResponseEntity.ok(savedUsed);
    }

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}

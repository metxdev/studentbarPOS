package ee.metxdev.tudengibaar.service;

import ee.metxdev.tudengibaar.entity.Role;
import ee.metxdev.tudengibaar.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImp implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImp(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role createRole(Role role) {
        if (role.getName() == null || role.getName().isBlank()) {
            throw new IllegalArgumentException("Role name must not be null or empty");
        }
        return roleRepository.save(role);
    }

    @Override
    public Optional<Role> getRoleByName(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}

package ee.metxdev.tudengibaar.service;

import ee.metxdev.tudengibaar.entity.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    Role createRole(Role role);
    Optional<Role> getRoleByName(String name);
    List<Role> getAllRoles();
}

package ua.kurapatkadev.coderscave.service;

import org.springframework.stereotype.Service;
import ua.kurapatkadev.coderscave.entities.Role;
import ua.kurapatkadev.coderscave.repository.RoleRepository;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
    public Role getUserRole(){
        return roleRepository.findByName("ROLE_USER").get();
    }
    public Role getAdminRole(){
        return roleRepository.findByName("ROLE_ADMIN").get();
    }
}

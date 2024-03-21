package org.example.spring_security.config;

import org.example.spring_security.entity.Role;
import org.example.spring_security.entity.User;
import org.example.spring_security.repository.RoleRepository;
import org.example.spring_security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitConfig implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserService userService;

    private void initRole() {
        if (roleRepository.findFirstByName("ROLE_ADMIN") == null)
            roleRepository.save(Role.builder().name("ROLE_ADMIN").build());
        if (roleRepository.findFirstByName("ROLE_USER") == null)
            roleRepository.save(Role.builder().name("ROLE_USER").build());
        if (userService.findUserByRole(roleRepository.findFirstByName("ROLE_ADMIN")) == null)
            userService.createUser(User.builder()
                    .name("admin")
                    .mail("admin@mail.fr")
                    .password("azerty")
                    .role(roleRepository.findFirstByName("ROLE_ADMIN"))
                    .build());

    }

    @Override
    public void run(String... args) throws Exception {
        initRole();
    }
}

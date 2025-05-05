package org.example.config;

import org.example.entities.AppUser;
import org.example.entities.Role;
import org.example.repositories.AppUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

        if(appUserRepository.findByUsername("admin").isEmpty()){
            AppUser newAdmin = new AppUser();
            newAdmin.setFullName("Admin User");
            newAdmin.setUsername("admin");
            newAdmin.setPassword(passwordEncoder.encode("Pollastrone26"));
            newAdmin.setRole(Role.ADMIN);
            appUserRepository.save(newAdmin);
            System.out.println("Admin User created. Username : 'admin' , Password : 'Pollastrone26'");
        }

    }
}

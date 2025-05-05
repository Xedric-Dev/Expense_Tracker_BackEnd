package org.example.services.implementations;

import org.example.entities.AppUser;
import org.example.repositories.AppUserRepository;
import org.example.services.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppUserServiceImpl implements AppUserService {

    @Autowired
    private AppUserRepository appUserRepository;


    public AppUser registerUser(AppUser user){

        return appUserRepository.save(user);

    }

    @Override
    public AppUser findByUsername(String username) {
        return appUserRepository.findByUsername(username).orElse(null);
    }

    @Override
    public Optional<AppUser> findById(Long id) {
        return appUserRepository.findById(id);
    }


}

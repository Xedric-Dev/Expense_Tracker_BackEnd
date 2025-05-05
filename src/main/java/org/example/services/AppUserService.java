package org.example.services;

import org.example.entities.AppUser;

import java.util.Optional;

public interface AppUserService {

    AppUser registerUser(AppUser user);

    AppUser findByUsername(String username);

    Optional<AppUser> findById(Long id);

}

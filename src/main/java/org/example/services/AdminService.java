package org.example.services;

import org.example.entities.AppUser;

import java.util.List;

public interface AdminService {

    List<AppUser> getAllUsers();
    boolean updateUserPassword(Long id);

}

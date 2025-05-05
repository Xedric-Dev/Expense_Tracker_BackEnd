package org.example.services.implementations;

import org.example.entities.AppUser;
import org.example.repositories.AppUserRepository;
import org.example.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Override
    public List<AppUser> getAllUsers() {
        return appUserRepository.findAll();
    }

    @Override
    public boolean updateUserPassword(Long id) {
        return false;
    }

}

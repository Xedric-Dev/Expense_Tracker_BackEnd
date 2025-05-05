package org.example.controllers;

import org.example.entities.AppUser;
import org.example.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/users")
    public ResponseEntity<List<AppUser>> getAllUser(){

        List<AppUser> users = adminService.getAllUsers();

        if(users.isEmpty()){
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }else return new ResponseEntity<>(users,HttpStatus.OK);

    }



}

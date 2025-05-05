package org.example.controllers;

import org.example.DTO.AuthResponseDTO;
import org.example.DTO.LogInRequestDTO;
import org.example.DTO.SignUpRequestDTO;
import org.example.services.implementations.AuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    AuthServiceImpl signUpService;



    @PostMapping("/signup")
    public ResponseEntity<AuthResponseDTO> signup(@RequestBody SignUpRequestDTO user){

        var response = signUpService.registerUser(user);

        if(response.getToken()!=null){
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }else return new ResponseEntity<>(response,HttpStatus.CONFLICT);



    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LogInRequestDTO user){

        var response = signUpService.loginUser(user);

        if(response.getToken()!=null){
            return new ResponseEntity<>(response,HttpStatus.OK);
        }else return new ResponseEntity<>(response,HttpStatus.UNAUTHORIZED);

    }




}

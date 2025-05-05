package org.example.services;

import org.example.DTO.AuthResponseDTO;
import org.example.DTO.LogInRequestDTO;
import org.example.DTO.SignUpRequestDTO;

public interface AuthService {

    AuthResponseDTO registerUser(SignUpRequestDTO signUpRequestDTO);
    AuthResponseDTO loginUser(LogInRequestDTO logInRequestDTO);

}

package org.example.services.implementations;

import org.example.DTO.AuthResponseDTO;
import org.example.DTO.LogInRequestDTO;
import org.example.DTO.SignUpRequestDTO;
import org.example.entities.AppUser;
import org.example.entities.Role;
import org.example.security.JwtUtil;
import org.example.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AppUserServiceImpl appUserService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthResponseDTO registerUser(SignUpRequestDTO requestDTO){

        if(appUserService.findByUsername(requestDTO.getUsername()) != null){
            return new AuthResponseDTO( null, "User " + requestDTO.getUsername() + " already registered.");
        }

        String encodedPassword = passwordEncoder.encode(requestDTO.getPassword());

        AppUser newUser = new AppUser();
        newUser.setFullName(requestDTO.getFullName());
        newUser.setUsername(requestDTO.getUsername());
        newUser.setPassword(encodedPassword);
        newUser.setRole(Role.USER);
        appUserService.registerUser(newUser);


        LogInRequestDTO userLogin = new LogInRequestDTO();
        userLogin.setUsername(requestDTO.getUsername());
        userLogin.setPassword(requestDTO.getPassword());

        return loginUser(userLogin);

    }

    @Override
    public AuthResponseDTO loginUser(LogInRequestDTO user) {

        String username = user.getUsername();
        String password = user.getPassword();

        try{

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username,password)
            );

            if (authentication.isAuthenticated()){
                return new AuthResponseDTO(
                        jwtUtil.generateToken(username),"success");
            }else{
                throw new BadCredentialsException("badCredentials");
            }
        } catch (Exception e){
            return new AuthResponseDTO(null, "Error : " + e.getMessage());
        }
    }
}

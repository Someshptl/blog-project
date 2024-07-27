package com.myblog.controller;

import com.myblog.entity.User;
import com.myblog.payload.LoginDto;
import com.myblog.payload.SignUp;
import com.myblog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //http://localhost:8080/api/auth/signup
    @PostMapping("/signup")
    public ResponseEntity<String> createUser(@RequestBody SignUp signUp){

        if(userRepository.existsByEmail(signUp.getEmail())){
            return new ResponseEntity<>("Email Id already in use", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (userRepository.existsByUsername(signUp.getUsername())){
            return new ResponseEntity<>("Username already in use",HttpStatus.INTERNAL_SERVER_ERROR);
        }

        User user = new User();
        user.setName(signUp.getName());
        user.setEmail(signUp.getEmail());
        user.setUsername(signUp.getUsername());
        user.setPassword(passwordEncoder.encode(signUp.getPassword()));

        userRepository.save(user);
        return new ResponseEntity<>("User is Registered Successfully",HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<String> signIn(LoginDto loginDto){

        //1 The constructor of UsernamePasswordAuthenticationToken will Supply loginDto.getUsername() to loadUserByUsername method in CustomUserDetailsService class.
        //2 It will compare
        //Expected credentials loginDto.getUsername(), loginDto.getPassword()
        //With Actual credentials given by loadUserByUsername method.
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        //It acts similar to Session variable
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new ResponseEntity<>("User Signed in successfully",HttpStatus.OK);
    }
}

package com.recipe.rohit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.recipe.rohit.config.JwtProvider;
import com.recipe.rohit.entity.User;
import com.recipe.rohit.repository.UserRepo;
import com.recipe.rohit.request.LoginRequest;
import com.recipe.rohit.response.AuthResponse;
import com.recipe.rohit.service.Impl.CustomUserDetailsService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signUp")
    public AuthResponse createUser(@RequestBody User user) throws Exception{

        String email = user.getEmail();
        String password = user.getPassword();
        String fullName = user.getFullName();

        User isExistUser = userRepo.findByEmail(email);
        if(isExistUser!=null){
            throw new Exception("Email is already used with another account..");
        }
        User createdUser = new User();
        createdUser.setEmail(email);
        createdUser.setPassword(passwordEncoder.encode(password));
        createdUser.setFullName(fullName);
        User saveUser =  userRepo.save(createdUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(email,password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateToken(authentication);
        return new AuthResponse(token,"SignUp Sucessfully !!");
    }

    @PostMapping("/login")
    public AuthResponse signInHandler(@RequestBody LoginRequest loginRequest){
        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        Authentication authentication = authenticate(username,password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateToken(authentication);
        return new AuthResponse(token,"LogIn Sucessfully !!");
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
        if(userDetails == null){
            throw new BadCredentialsException("User not found .....");
        }if(!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new BadCredentialsException("Inavlid password..");
        }
        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }
}

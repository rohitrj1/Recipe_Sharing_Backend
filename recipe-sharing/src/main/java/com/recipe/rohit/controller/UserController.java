package com.recipe.rohit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.recipe.rohit.config.JwtConstant;
import com.recipe.rohit.entity.User;
import com.recipe.rohit.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;



    @GetMapping("/profile")
    public ResponseEntity<User> findUserByJwt(@RequestHeader(JwtConstant.JWT_HEADER) String jwt) throws Exception {
        User user = userService.findUserByJwt(jwt);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) throws Exception {
        User saveUser = userService.saveUser(user);
        return new ResponseEntity<>(saveUser, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUser(){
        return new ResponseEntity<>(userService.getAllUser(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getSingleUser(@PathVariable Long id){
        return new ResponseEntity<>(userService.findByUserId(id),HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> createUser(@PathVariable Long id) {
      userService.deleteUser(id);
        return ResponseEntity.ok("User deleted sucessfully");
    }
}

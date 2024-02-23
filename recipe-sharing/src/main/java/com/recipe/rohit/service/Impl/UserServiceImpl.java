package com.recipe.rohit.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.recipe.rohit.config.JwtProvider;
import com.recipe.rohit.entity.User;
import com.recipe.rohit.repository.UserRepo;
import com.recipe.rohit.service.UserService;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    public User saveUser(User user) throws Exception {
        User isExist = userRepo.findByEmail(user.getEmail());
        if(isExist!=null){
            throw new Exception("user is exist with " + user.getEmail());
        }
        return userRepo.save(user);
    }

    @Override
    public List<User> getAllUser() {
        return userRepo.findAll();
    }

    @Override
    public User findByUserId(Long id) {
        return userRepo.findById(id).orElseThrow(()->new RuntimeException("user does not exist..." + id));
    }

    @Override
    public void deleteUser(Long id) {
      this.userRepo.deleteById(id);
    }

    @Override
    public User findUserByJwt(String jwt) throws Exception {
        String email = jwtProvider.getEmailFromJwtToken(jwt);
        if(email==null){
            throw new Exception("Provide valid jwt token....");
        }

     User user =  userRepo.findByEmail(email);
        if(user==null){
            throw new Exception("user not found with email "+email);
        }
        return user;
    }
}

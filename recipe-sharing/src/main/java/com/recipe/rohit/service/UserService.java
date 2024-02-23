package com.recipe.rohit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.recipe.rohit.entity.User;
import com.recipe.rohit.repository.UserRepo;

import java.util.List;


public interface UserService {

    public User saveUser(User user) throws Exception;

    public List<User> getAllUser();

    public User findByUserId(Long id);

    public void deleteUser(Long id);

    public User findUserByJwt(String jwt) throws Exception;
}

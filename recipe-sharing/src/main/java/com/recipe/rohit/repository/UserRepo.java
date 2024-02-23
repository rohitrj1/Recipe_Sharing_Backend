package com.recipe.rohit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.recipe.rohit.entity.User;

public interface UserRepo extends JpaRepository<User,Long> {

    public User findByEmail(String email);

}

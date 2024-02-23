package com.recipe.rohit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.recipe.rohit.entity.Recipe;

public interface RecipeRepository extends JpaRepository<Recipe,Long> {
}

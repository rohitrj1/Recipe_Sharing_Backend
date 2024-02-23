package com.recipe.rohit.service;

import java.util.List;

import com.recipe.rohit.entity.Recipe;
import com.recipe.rohit.entity.User;

public interface ReceipeService {
    public Recipe createRecipe(Recipe recipe, User user);
    public Recipe findRecipeById(Long id) throws Exception;

    public void deleteRecipe(Long id) throws Exception;
    public Recipe updateRecipe(Recipe recipe,Long id) throws Exception;
    public List<Recipe> findAllRecipe();
    public Recipe likeRecipe(Long recipeId,User user) throws Exception;
}

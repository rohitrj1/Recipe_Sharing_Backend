package com.recipe.rohit.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.recipe.rohit.entity.Recipe;
import com.recipe.rohit.entity.User;
import com.recipe.rohit.repository.RecipeRepository;
import com.recipe.rohit.service.ReceipeService;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RecipeServiceImpl implements ReceipeService {

    @Autowired
    private RecipeRepository recipeRepository;
    @Override
    public Recipe createRecipe(Recipe recipe, User user) {
        recipe.setCreatedAt(LocalDateTime.now());
        recipe.setUser(user);
        return recipeRepository.save(recipe);
    }

    @Override
    public Recipe findRecipeById(Long id) throws Exception {
        return recipeRepository.findById(id).orElseThrow(()->new RuntimeException("user doesnot exist " +id));
    }

    @Override
    public void deleteRecipe(Long id) throws Exception {
       this.recipeRepository.deleteById(id);
    }

    @Override
    public Recipe updateRecipe(Recipe recipe, Long id) throws Exception {
        Recipe odlRecipe = findRecipeById(id);
        if(recipe.getTitle()!=null){
            odlRecipe.setTitle(recipe.getTitle());
        }if(recipe.getImage()!=null){
            odlRecipe.setImage(recipe.getImage());
        }if(recipe.getDescription()!=null){
            odlRecipe.setDescription(recipe.getDescription());
        }
        return recipeRepository.save(odlRecipe);
    }

    @Override
    public List<Recipe> findAllRecipe() {
        return recipeRepository.findAll();
    }

    @Override
    public Recipe likeRecipe(Long recipeId, User user) throws Exception {
        Recipe recipe = findRecipeById(recipeId);
        if(recipe.getLikes().contains(user.getId())){
            recipe.getLikes().remove(user.getId());
        }else{
            recipe.getLikes().add(user.getId());
        }
        return recipeRepository.save(recipe);
    }
}

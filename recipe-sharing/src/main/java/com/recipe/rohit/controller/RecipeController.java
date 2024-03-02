package com.recipe.rohit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.recipe.rohit.config.JwtConstant;
import com.recipe.rohit.entity.Recipe;
import com.recipe.rohit.entity.User;
import com.recipe.rohit.service.ReceipeService;
import com.recipe.rohit.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/recipe")
public class RecipeController {

    @Autowired
    private ReceipeService receipeService;

    @Autowired
    private UserService userService;


    @PostMapping
    public ResponseEntity<Recipe> createRecipe(@RequestBody Recipe recipe , @RequestHeader(JwtConstant.JWT_HEADER) String jwt) throws Exception {
        User user = userService.findUserByJwt(jwt);
        return new ResponseEntity<>(receipeService.createRecipe(recipe,user), HttpStatus.CREATED);
    }

//    @PostMapping("/user/{userId}")
//    public ResponseEntity<Recipe> createRecipe(@RequestBody Recipe recipe , @PathVariable Long userId){
//        User user = userService.findByUserId(userId);
//        return new ResponseEntity<>(receipeService.createRecipe(recipe,user), HttpStatus.CREATED);
//    }

    @GetMapping
    public ResponseEntity<List<Recipe>> getAllRecipe(){
        return new ResponseEntity<>(receipeService.findAllRecipe(),HttpStatus.OK);
    }

    @GetMapping("/{recipeId}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable Long recipeId) throws Exception {
        return new ResponseEntity<>(receipeService.findRecipeById(recipeId),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRecipe(@PathVariable Long id) throws Exception {
        receipeService.deleteRecipe(id);
        return ResponseEntity.ok("Sucessfully Recipe deleted ..");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Recipe> updatedRecipe(@RequestBody Recipe recipe,@PathVariable Long id) throws Exception {
        return new ResponseEntity<>(receipeService.updateRecipe(recipe,id),HttpStatus.OK);
    }

    @PutMapping("/{id}/like")
    public ResponseEntity<Recipe> likeRecipe(@RequestHeader(JwtConstant.JWT_HEADER) String jwt,@PathVariable Long id) throws Exception {
        User user = userService.findUserByJwt(jwt);
        return new ResponseEntity<>( receipeService.likeRecipe(id,user),HttpStatus.OK);
    }

//    @PutMapping("/{recipeId}/user/{userId}")
//    public ResponseEntity<Recipe> likeRecipe(@PathVariable Long userId,@PathVariable Long recipeId) throws Exception {
//        User user = userService.findByUserId(userId);
//        return new ResponseEntity<>( receipeService.likeRecipe(recipeId,user),HttpStatus.OK);
//    }
}

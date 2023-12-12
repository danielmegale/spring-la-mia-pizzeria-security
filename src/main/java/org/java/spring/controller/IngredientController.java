package org.java.spring.controller;


import org.java.spring.db.pojo.Ingredient;
import org.java.spring.db.pojo.Pizza;
import org.java.spring.db.service.IngredientService; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class IngredientController {

    @Autowired
    private IngredientService ingredientService;

    @GetMapping("/ingredients")
    public String listIngredients(Model model) {
        List<Ingredient> ingredients = ingredientService.findAll();
        model.addAttribute("ingredients", ingredients);
        return "ingredient-list";
    }

    @GetMapping("/ingredients/create")
    public String createIngredient(Model model) {
        Ingredient ingredient = new Ingredient();
        model.addAttribute("ingredient", ingredient);
        return "ingredient-form";
    }

    @PostMapping("/ingredients/create")
    public String storeIngredient(@ModelAttribute Ingredient ingredient) {
        ingredientService.save(ingredient);
        return "redirect:/ingredients";
    }

    @GetMapping("/ingredients/edit/{id}")
    public String editIngredient(@PathVariable int id, Model model) {
        Ingredient ingredient = ingredientService.findById(id);
        if (ingredient != null) {
            model.addAttribute("ingredient", ingredient);
            model.addAttribute("actionUrl", "/ingredients/edit/" + id);
            return "ingredient-form";
        } else {
            return "redirect:/ingredients";
        }
    }

    @PostMapping("/ingredients/edit/{id}")
    public String updateIngredient(@PathVariable int id, @ModelAttribute Ingredient ingredient) {
        ingredient.setId(id);
        ingredientService.save(ingredient);

        return "redirect:/ingredients";
    }

    
    @PostMapping("/ingredients/delete/{id}")
    public String deleteIngredient(@PathVariable int id) {
        Ingredient ingredient = ingredientService.findById(id);
        
        for (Pizza pizza : ingredient.getPizzas()) {
            pizza.getIngredients().remove(ingredient);
        }
        
        ingredientService.delete(ingredient);
        return "redirect:/ingredients";
    }

}

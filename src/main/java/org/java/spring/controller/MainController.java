package org.java.spring.controller;

import java.util.List;

import org.java.spring.db.pojo.Coupon;
import org.java.spring.db.pojo.Ingredient;
import org.java.spring.db.pojo.Pizza;
import org.java.spring.db.service.CouponService;
import org.java.spring.db.service.IngredientService;
import org.java.spring.db.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;

@Controller
public class MainController {

	@Autowired
	private PizzaService pizzaService;
	
	@Autowired
	private IngredientService ingredientService;
	
	@Autowired
	private CouponService couponService;

	@GetMapping()
	public String searchPizzas(Model model, @RequestParam(required = false) String q,Authentication auth) {

		List<Pizza> pizzas = q == null ? pizzaService.findAll() : pizzaService.findByTitle(q);

		model.addAttribute("pizzas", pizzas);
		model.addAttribute("q", q == null ? "" : q);
		
		System.out.println(
				auth == null
				? "No logged in"
				: "User" + auth.getName());

		return "Pizzas";
	}

	@GetMapping("/pizzas/create")
	public String createPizza(Model model) {
		List<Ingredient>  ingredients = ingredientService.findAll();
		Pizza pizza = new Pizza();
		model.addAttribute("ingredients",ingredients );
		model.addAttribute("pizza", pizza);
		return "pizza-form";
	}
	@PostMapping("/pizzas/create")
	public String storePizza(Model model, @Valid @ModelAttribute Pizza pizza, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("pizza", pizza);
			return "pizza-form";
		}
		pizzaService.save(pizza);
		return "redirect:/";

	}

	@GetMapping("/pizzas/{id}")
	public String getPizzaId(Model model, @PathVariable int id,Authentication auth) {
		Pizza pizzaId = pizzaService.findById(id);
		List<Coupon> coupons =pizzaId.getCoupons();
		List<Ingredient>  ingredients = pizzaId.getIngredients();
		model.addAttribute("pizzaId", pizzaId);
		model.addAttribute("coupon", coupons);
		model.addAttribute("ingredients",ingredients );
		return "pizza-detail";

	}
	
	@GetMapping("/pizzas/edit/{id}")
	public String editPizza(Model model,@PathVariable int id) {
		List<Ingredient>  ingredients = ingredientService.findAll();
		Pizza pizza = pizzaService.findById(id);
		model.addAttribute("ingredients",ingredients );
		model.addAttribute("pizza", pizza);
		return"pizza-form";
	}
	@PostMapping("/pizzas/edit/{id}")
	public String updatePizza(Model model,@Valid @ModelAttribute Pizza pizza, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("pizza", pizza);
			return "pizza-form";
		}
		pizzaService.save(pizza);
		return "redirect:/";
	}
	
	@PostMapping("/pizzas/delete/{id}")
	public String deletePizza(@PathVariable int id) {
		Pizza pizza=pizzaService.findById(id);
		
		pizza.clear();
		pizzaService.save(pizza);
		
		List<Coupon> coupons=pizza.getCoupons();
		coupons.forEach(couponService::delete);
		
		pizzaService.delete(pizza);
		return "redirect:/";
	}
}

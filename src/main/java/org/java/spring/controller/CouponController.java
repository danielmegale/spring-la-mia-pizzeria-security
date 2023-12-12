package org.java.spring.controller;

import org.java.spring.db.pojo.Coupon;
import org.java.spring.db.pojo.Pizza;
import org.java.spring.db.service.CouponService;
import org.java.spring.db.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.validation.Valid;

@Controller
public class CouponController {

	@Autowired
	private PizzaService pizzaService;

	@Autowired
	private CouponService couponService;

	@GetMapping("/pizzas/{id}/coupon")
	public String createCouponForm(Model model, @PathVariable int id) {

		Pizza pizza = pizzaService.findById(id);
		model.addAttribute("pizza", pizza);

		Coupon coupon = new Coupon();
		model.addAttribute("coupon", coupon);

		return "coupon-form";
	}

	@PostMapping("/pizzas/{id}/coupon")
	public String storeCoupon(@PathVariable int id, @ModelAttribute Coupon coupon) {

		Pizza pizza = pizzaService.findById(id);

		Coupon newCoupon = new Coupon(coupon.getTitolo(), coupon.getDataDiInzio(), coupon.getDataDiFine(), pizza);

		couponService.save(newCoupon);

		return "redirect:/";
	}

	@GetMapping("/pizzas/{id}/coupon/edit")
	public String editCoupon(Model model, @PathVariable int id) {
		Coupon coupon = couponService.findById(id);
		model.addAttribute("coupon", coupon);
		return "coupon-form";
	}

	@PostMapping("/pizzas/{id}/coupon/edit")
	public String updateCoupon(Model model, @PathVariable int id, @Valid @ModelAttribute Coupon updatedCoupon,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("coupon", updatedCoupon);
			return "coupon-form";
		}
		Coupon existingCoupon = couponService.findById(id);
		if (existingCoupon != null) {
			existingCoupon.setTitolo(updatedCoupon.getTitolo());
			existingCoupon.setDataDiInzio(updatedCoupon.getDataDiInzio());
			existingCoupon.setDataDiFine(updatedCoupon.getDataDiFine());
			couponService.save(existingCoupon);
		}
		return "redirect:/";
	}

	@PostMapping("/pizzas/{pizzaId}/deleteCoupon/{couponId}")
	public String deleteCoupon(@PathVariable int pizzaId, @PathVariable int couponId) {
		Coupon coupon = couponService.findById(couponId);
		if (coupon != null) {
			if (coupon.getPizza().getId() == pizzaId) {
				couponService.delete(coupon);
			}
		}
		return "redirect:/";
	}
}

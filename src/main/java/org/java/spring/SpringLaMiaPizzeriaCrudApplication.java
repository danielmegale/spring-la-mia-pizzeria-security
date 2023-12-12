package org.java.spring;

import java.time.LocalDate;
import java.util.List;

import org.java.spring.auth.conf.AuthConf;
import org.java.spring.auth.db.pojo.Role;
import org.java.spring.auth.db.pojo.User;
import org.java.spring.auth.db.service.RoleService;
import org.java.spring.auth.db.service.UserService;
import org.java.spring.db.pojo.Coupon;
import org.java.spring.db.pojo.Ingredient;
import org.java.spring.db.pojo.Pizza;
import org.java.spring.db.service.CouponService;
import org.java.spring.db.service.IngredientService;
import org.java.spring.db.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringLaMiaPizzeriaCrudApplication implements CommandLineRunner {

	@Autowired
	private PizzaService pizzaService;
	@Autowired
	private CouponService couponService;
	@Autowired
	private IngredientService ingredientService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(SpringLaMiaPizzeriaCrudApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Ingredient ing1 = new Ingredient("nome1");
		Ingredient ing2 = new Ingredient("nome2");
		Ingredient ing3 = new Ingredient("nome3");
		Ingredient ing4 = new Ingredient("nome4");

		ingredientService.save(ing1);
		ingredientService.save(ing2);
		ingredientService.save(ing3);
		ingredientService.save(ing4);

		pizzaService.save(new Pizza("Margherita", "Pizza con modoro e mozzarella", 4.99, null, ing1, ing2));
		pizzaService.save(
				new Pizza("Patatosa", "Pizza con modoro, mozzarella e patatine fritte", 6.99, null, ing1, ing2, ing4));
		pizzaService.save(new Pizza("4 Formaggi",
				"Pizza con mozzarella, Certosino, Gorgonzola D.O.P. Gim e parmigiano grattugiato,", 6.99, null, ing2,
				ing4));
		pizzaService.save(new Pizza("Prosciutto cotto", "Pizza con modoro, mozzarella e prosciutto cotto", 6.99, null,
				ing3, ing1));

		List<Pizza> pizzas = pizzaService.findAll();
		couponService.save(new Coupon("coupon 1", LocalDate.now(), LocalDate.now().plusDays(5), pizzas.get(0)));
		couponService.save(new Coupon("coupon 2", LocalDate.now(), LocalDate.now().plusDays(10), pizzas.get(1)));
		couponService.save(new Coupon("coupon 3", LocalDate.now(), LocalDate.now().plusDays(3), pizzas.get(2)));
		couponService.save(new Coupon("coupon 4", LocalDate.now(), LocalDate.now().plusDays(8), pizzas.get(3)));

		
		Role roleUser = new Role("USER");
		Role roleAdmin = new Role("ADMIN");

		roleService.save(roleUser);
		roleService.save(roleAdmin);
		
		String pws =AuthConf.passwordEncoder().encode("pws");

		User user1 = new User("user1", pws, roleUser);
		User admin = new User("admin", pws, roleAdmin);

		userService.save(user1);
		userService.save(admin);

	}
}

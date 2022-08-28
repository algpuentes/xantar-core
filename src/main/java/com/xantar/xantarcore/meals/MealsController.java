package com.xantar.xantarcore.meals;

import com.xantar.xantarcore.db.MealsDbService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(MealsController.ENDPOINT_API_MEALS)
public class MealsController {

	static final String ENDPOINT_API_MEALS = "/api/meals";

	Logger	LOGGER = LoggerFactory.getLogger(MealsController.class);

	private final MealsDbService mealsDbService;

	@Autowired
	MealsController(MealsDbService mealsDbService) {
		this.mealsDbService = Objects.requireNonNull(mealsDbService, "Meals Service must not be null");
	}

	@GetMapping("/{mealId}")
	public ResponseEntity<MealResponseJson> findMeal(@PathVariable("mealId") Integer mealId) {
		return this.mealsDbService.findById(mealId)
				.map(meal -> new ResponseEntity<>(new MealResponseJson(meal), HttpStatus.OK))
				.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping()
	public ResponseEntity<List<MealResponseJson>> findAllMeals() {
		var list = this.mealsDbService.findAll().stream()
				.map(MealResponseJson::new)
				.collect(Collectors.toList());
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@PostMapping()
	public ResponseEntity<MealResponseJson> createMeal(@RequestBody MealResponseJson meal) {
		return Optional.ofNullable(meal)
				.map(jMeal -> this.mealsDbService.createMeal(jMeal.toMeal()))
				.map(savedMeal -> new ResponseEntity<>(new MealResponseJson(savedMeal), HttpStatus.CREATED))
				.orElse(ResponseEntity.badRequest().build());
	}

	@PatchMapping("/{mealId}")
	public ResponseEntity<MealResponseJson> updateMeal(@PathVariable("mealId") Integer mealId, @RequestBody MealResponseJson meal) {
		return Optional.ofNullable(meal)
				.map(MealResponseJson::toMeal)
				.map(m -> Meal.builder(m).withId(mealId).build())
				.map(this.mealsDbService::updateMeal)
				.map(patchedMeal -> new ResponseEntity<>(new MealResponseJson(patchedMeal), HttpStatus.OK))
				.orElse(ResponseEntity.badRequest().build());
	}



	@DeleteMapping("/{mealId}")
	public ResponseEntity<?> deleteMeal(@PathVariable("mealId") int mealId) {
		this.mealsDbService.deleteMeal(mealId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}

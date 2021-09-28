package com.xantar.xantarcore.meals;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xantar.xantarcore.db.MealsService;
import com.xantar.xantarcore.models.Meal;

@RestController
@RequestMapping("/api/meals")
public class MealsController {

	Logger	LOGGER = LoggerFactory.getLogger(MealsController.class);

	private final MealsService	mealsService;

	@Autowired
	MealsController(MealsService mealsService) {
		if (mealsService == null) {
			throw new IllegalArgumentException("Meals Service must not be null");
		}

		this.mealsService = mealsService;
	}

	@GetMapping("/{mealId}")
	public ResponseEntity<MealResponseJson> findMeal(@PathVariable("mealId") Integer mealId) {
		final MealResponseJson jMeal = MealResponseMapper.toJsonResponse(this.mealsService.findById(mealId));
		return new ResponseEntity<MealResponseJson>(jMeal, HttpStatus.OK);
	}

	@GetMapping()
	public ResponseEntity<List<MealResponseJson>> findAllMeals() {
		final List<MealResponseJson> list = this.mealsService.findAll().stream()
				.map(meal -> MealResponseMapper.toJsonResponse(meal))
				.collect(Collectors.toList());
		return new ResponseEntity<List<MealResponseJson>>(list, HttpStatus.OK);
	}

	@PostMapping()
	public ResponseEntity<MealResponseJson> createMeal(@RequestBody MealResponseJson jMeal) {
		final Meal mealModel = MealResponseMapper.toModel(jMeal);
		final Meal createdMeal = this.mealsService.createMeal(mealModel);
		final MealResponseJson responseJMeal = MealResponseMapper.toJsonResponse(createdMeal);

		return new ResponseEntity<MealResponseJson>(responseJMeal, HttpStatus.CREATED);
	}

	@PatchMapping("/{mealId}")
	public ResponseEntity<MealResponseJson> updateMeal(@PathVariable("mealId") Integer mealId, @RequestBody MealResponseJson data) {
		final MealResponseJson jMeal = new MealResponseJson.Builder().withId(mealId)
				.withName(data.name)
				.withDescription(data.description)
				.withSlots(data.slots)
				.withImageThumb(data.imageThumb)
				.build();
		final Meal mealModel = MealResponseMapper.toModel(jMeal);
		final Meal createdMeal = this.mealsService.updateMeal(mealModel);
		final MealResponseJson responseJMeal = MealResponseMapper.toJsonResponse(createdMeal);

		return new ResponseEntity<MealResponseJson>(responseJMeal, HttpStatus.OK);
	}

	@DeleteMapping("/{mealId}")
	public ResponseEntity deleteMeal(@PathVariable("mealId") Integer mealId) {
		this.mealsService.deleteMeal(mealId);
		return new ResponseEntity(HttpStatus.OK);
	}
}

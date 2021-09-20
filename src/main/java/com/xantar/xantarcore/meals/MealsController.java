package com.xantar.xantarcore.meals;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xantar.xantarcore.db.MealsService;
import com.xantar.xantarcore.models.Meal;

@RestController
@RequestMapping("/api/meals")
public class MealsController {

	Logger						LOGGER	= LoggerFactory.getLogger(MealsController.class);

	private final MealsService	mealsService;

	@Autowired
	MealsController(MealsService mealsService) {
		if (mealsService == null) {
			throw new IllegalArgumentException("Meals Service must not be null");
		}

		this.mealsService = mealsService;
	}

	@GetMapping()
	public List<MealResponseJson> findAllMeals() {
		return this.mealsService.findAll().stream()
				.map(meal -> MealResponseMapper.toJsonResponse(meal))
				.collect(Collectors.toList());
	}

	@PostMapping()
	public MealResponseJson createMeal(@RequestBody MealResponseJson jMeal) {
		final Meal mealModel = MealResponseMapper.toModel(jMeal);
		final Meal createdMeal = this.mealsService.createMeal(mealModel);

		return MealResponseMapper.toJsonResponse(createdMeal);
	}

}

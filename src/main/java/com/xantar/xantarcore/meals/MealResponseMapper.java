package com.xantar.xantarcore.meals;

import com.xantar.xantarcore.models.Meal;

public class MealResponseMapper {

	public static Meal toModel(MealResponseJson jMeal) {
		return new Meal(jMeal.id, jMeal.name, jMeal.description);
	}

	public static MealResponseJson toJsonResponse(Meal meal) {
		return new MealResponseJson(meal.id, meal.name, meal.description);
	}

}

package com.xantar.xantarcore.meals;

import java.util.List;
import java.util.stream.Collectors;

import com.xantar.xantarcore.models.Meal;

public class MealResponseMapper {

	public static Meal toModel(MealResponseJson jMeal) {
		//TODO: handle slots
		return new Meal(jMeal.id, jMeal.name, jMeal.description, null);
	}

	public static MealResponseJson toJsonResponse(Meal meal) {
		final List<String> slots = meal.slots.stream().map(slot -> slot.name).collect(Collectors.toList());
		return new MealResponseJson(meal.id, meal.name, meal.description, slots);
	}

}

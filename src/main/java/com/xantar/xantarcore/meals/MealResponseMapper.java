package com.xantar.xantarcore.meals;

import java.util.List;
import java.util.stream.Collectors;

import com.xantar.xantarcore.models.Meal;
import com.xantar.xantarcore.models.Slot;
import com.xantar.xantarcore.utils.Base64Utils;

public class MealResponseMapper {

	public static Meal toModel(MealResponseJson jMeal) {
		if(jMeal == null) {
			return null;
		}

		final List<Slot> slots = jMeal.slots == null ? null : jMeal.slots.stream().map(slot -> SlotResponseMapper.toModel(slot)).collect(Collectors.toList());
		final byte[] imageBytes = Base64Utils.decodeImage(jMeal.imageThumb);

		return new Meal(jMeal.id, jMeal.name, jMeal.description, slots, imageBytes);
	}

	public static MealResponseJson toJsonResponse(Meal meal) {
		if(meal == null) {
			return null;
		}

		final List<SlotResponseJson> slots = meal.slots == null ? null : meal.slots.stream().map(slot -> SlotResponseMapper.toJsonResponse(slot)).collect(Collectors.toList());
		final String base64Image = Base64Utils.encodeImage(meal.imageThumb);

		return new MealResponseJson(meal.id, meal.name, meal.description, slots, base64Image);
	}

}

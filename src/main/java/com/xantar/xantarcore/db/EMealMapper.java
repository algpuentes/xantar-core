package com.xantar.xantarcore.db;

import com.xantar.xantarcore.models.Meal;

public class EMealMapper {

	public static Meal toModel(EMeal eMeal) {
		return new Meal(eMeal.id, eMeal.name, eMeal.description);
	}

	public static EMeal toEntity(Meal meal) {
		final var eMeal = new EMeal();
		eMeal.id = meal.id;
		eMeal.name = meal.name;
		eMeal.description = meal.description;
		return eMeal;
	}

}

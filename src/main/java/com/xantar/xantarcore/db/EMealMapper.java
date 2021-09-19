package com.xantar.xantarcore.db;

import java.util.List;
import java.util.stream.Collectors;

import com.xantar.xantarcore.models.Meal;
import com.xantar.xantarcore.models.Slot;

public class EMealMapper {

	public static Meal toModel(EMeal eMeal) {
		final List<Slot> slots = eMeal.slots.stream().map(eSlot -> ESlotMapper.toModel(eSlot)).collect(Collectors.toList());

		return new Meal(eMeal.id, eMeal.name, eMeal.description, slots, eMeal.imageThumb);
	}

	public static EMeal toEntity(Meal meal) {
		final var eMeal = new EMeal();
		eMeal.id = meal.id;
		eMeal.name = meal.name;
		eMeal.description = meal.description;

		return eMeal;
	}

}

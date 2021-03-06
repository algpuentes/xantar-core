package com.xantar.xantarcore.db;

import java.util.stream.Collectors;

import com.xantar.xantarcore.models.Meal;

public class EMealMapper {

	public static Meal toModel(EMeal eMeal) {
		if(eMeal == null) {
			return null;
		}

		final var slots =
				eMeal.slots == null ? null :
					eMeal.slots.stream()
					.map(eSlot -> ESlotMapper.toModel(eSlot))
					.collect(Collectors.toList());

		return new Meal(eMeal.id, eMeal.name, eMeal.description, slots, eMeal.imageThumb);
	}

	public static EMeal toEntity(Meal meal) {
		if(meal == null) {
			return null;
		}

		final var eMeal = new EMeal();
		eMeal.id = meal.id;
		eMeal.name = meal.name;
		eMeal.description = meal.description;

		final var eSlots =
				meal.slots == null ? null :
					meal.slots.stream()
					.map(slot -> ESlotMapper.toEntity(slot))
					.collect(Collectors.toSet());
		eMeal.slots = eSlots;
		eMeal.imageThumb = meal.imageThumb;

		return eMeal;
	}

}

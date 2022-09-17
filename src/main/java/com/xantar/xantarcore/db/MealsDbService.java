package com.xantar.xantarcore.db;

import java.util.*;
import java.util.stream.Collectors;

import com.xantar.xantarcore.meals.Meal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MealsDbService {

	private final IMealsRepository	mealsRepository;

	@Autowired
	public MealsDbService(IMealsRepository mealsRepository) {
		this.mealsRepository = Objects.requireNonNull(mealsRepository, "Meals repository must not be null");
	}

	public Optional<Meal> findById(Integer mealId) {
		return Optional.ofNullable(mealId)
				.flatMap(this.mealsRepository::findById)
				.map(EMeal::toMeal);
	}

	public List<Meal> findAll() {
		return Optional.of(this.mealsRepository.findAll())
				.orElse(new ArrayList<>())
				.stream()
				.map(EMeal::toMeal)
				.collect(Collectors.toList());
	}

	public Meal createMeal(Meal meal) {
		Objects.requireNonNull(meal, "Cannot store [null] meal");
		final EMeal eMeal = new EMeal(meal);

		return this.mealsRepository.save(eMeal).toMeal();
	}

	public Meal updateMeal(Meal dataToUpdate) {
		return Optional.ofNullable(dataToUpdate)
				.filter(meal -> meal.id() != null)
				.map(meal -> this.mealsRepository.findById(meal.id()))
				.orElseThrow(() -> new IllegalArgumentException("Cannot update null value as meal"))
				.map(eMeal ->  buildEMealWithUpdatedData(eMeal, dataToUpdate))
				.map(updatedEMeal -> this.mealsRepository.save(updatedEMeal).toMeal())
				.orElseThrow(() -> new RuntimeException("No meal found for id [" + dataToUpdate.id() + "]"));
	}

	public void deleteMeal(int mealId) {
		this.mealsRepository.deleteById(mealId);
	}

	public List<Meal> findMealsBySlotAndMaxRows(int slotId, int maxRows) {
		return this.mealsRepository.findMealsBySlotAndMaxRows(slotId, maxRows)
				.stream()
				.map(EMeal::toMeal)
				.collect(Collectors.toList());
	}

	/**
	 * Updates only non null values
	 * **/
	private EMeal buildEMealWithUpdatedData(EMeal originalEMeal, Meal updatedData) {

		var eFinalMeal =  new EMeal(Meal.builder()
				.withId(originalEMeal.id)
				.withName(updatedData.name() != null ? updatedData.name().strip() : originalEMeal.name)
				.withDescription(updatedData.description()  != null ? updatedData.description().strip() : originalEMeal.description)
				.withImageThumb(updatedData.imageThumb() != null ? updatedData.imageThumb() : originalEMeal.imageThumb)
				.build());

		Optional.of(updatedData.slots())
				.filter(slots -> !slots.isEmpty())
		.ifPresentOrElse(slots -> slots.forEach(slot -> eFinalMeal.addSlot(new ESlot(slot))),
				() -> originalEMeal.slots.forEach(eFinalMeal::addSlot));

		return eFinalMeal;
	}

}

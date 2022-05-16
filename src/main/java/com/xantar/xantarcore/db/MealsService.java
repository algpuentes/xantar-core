package com.xantar.xantarcore.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MealsService {

	private final Logger LOGGER	= LoggerFactory.getLogger(MealsService.class);

	private final IMealsRepository	mealsRepository;

	@Autowired
	public MealsService(IMealsRepository mealsRepository) {
		if (mealsRepository == null) {
			throw new IllegalArgumentException("Meals repository must not be null");
		}

		this.mealsRepository = mealsRepository;
	}

	public Meal findById(Integer id) {
		return new Meal(this.mealsRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Meal with id ''" + id + " not found.")));
	}

	public List<Meal> findAll() {
		return Optional.ofNullable(this.mealsRepository.findAll())
				.orElse(new ArrayList<EMeal>())
				.stream()
				.map(eMeal -> new Meal(eMeal))
				.collect(Collectors.toList());
	}

	public Meal createMeal(Meal meal) {
		final EMeal eMeal = new EMeal(meal);

		if(eMeal.name == null || eMeal.name.isBlank()) {
			throw new IllegalArgumentException("Cannot insert empty value as name");
		}
		return new Meal(this.mealsRepository.save(eMeal));
	}

	public Meal updateMeal(Meal updatedData) {
		if(updatedData == null || updatedData.id == null) {
			throw new IllegalArgumentException("Cannot update null value as meal");
		}

		final EMeal originalEMeal = this.mealsRepository.findById(updatedData.id)
				.orElseThrow(() -> new EntityNotFoundException("Meal with id ''" + updatedData.id + " not found."));

		// TODO: check if there is a better and easy way
		final EMeal updateEMeal = this.updateEntity(originalEMeal, updatedData);

		return new Meal(this.mealsRepository.save(updateEMeal));
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
	private EMeal updateEntity(EMeal originalEMeal, Meal updatedData) {
		if(updatedData.name != null && updatedData.name.isBlank()) {
			throw new IllegalArgumentException("Cannot insert empty value as name");
		}

		final EMeal eMeal =  EMeal.builder()
				.withId(originalEMeal.id)
				.withName(updatedData.name != null ? updatedData.name : originalEMeal.name)
				.withDescription(updatedData.description  != null ? updatedData.description : originalEMeal.description)
				.withImageThumb(updatedData.imageThumb != null ? updatedData.imageThumb : originalEMeal.imageThumb)
				.build();

		// TODO: check if this would work as expected
		Optional.ofNullable(updatedData.slots)
		.ifPresentOrElse(slots -> slots.stream().forEach(slot -> eMeal.addSlot(new ESlot(slot))),
				() -> originalEMeal.slots.stream().forEach(slot -> eMeal.addSlot(slot)));

		return eMeal;
	}

}

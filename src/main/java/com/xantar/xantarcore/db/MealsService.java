package com.xantar.xantarcore.db;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xantar.xantarcore.models.Meal;

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

	public List<Meal> findAll() {
		return this.mealsRepository.findAll().stream()
				.map(eMeal -> EMealMapper.toModel(eMeal))
				.collect(Collectors.toList());
	}

	public Meal createMeal(Meal meal) {
		final EMeal eMeal = EMealMapper.toEntity(meal);
		if(eMeal == null) {
			throw new IllegalArgumentException("Cannot insert null value as meal");
		}
		return EMealMapper.toModel(this.mealsRepository.save(eMeal));
	}

	public Meal updateMeal(Meal updatedData) {
		if(updatedData == null || updatedData.id == null) {
			throw new IllegalArgumentException("Cannot update null value as meal");
		}

		final EMeal originalEMeal = this.mealsRepository.findById(updatedData.id)
				.orElseThrow(() -> new EntityNotFoundException("Meal with id ''" + updatedData.id));

		final EMeal updateEMeal = this.updateEntity(originalEMeal, updatedData);

		return EMealMapper.toModel(this.mealsRepository.save(updateEMeal));
	}

	/**
	 * Updates just non null values
	 * **/
	private EMeal updateEntity(EMeal originalEMeal, Meal updatedData) {
		final EMeal eMeal =  new EMeal.EMealBuilder()
				.withId(originalEMeal.id)
				.withName(updatedData.name != null ? updatedData.name : originalEMeal.name)
				.withDescription(updatedData.description  != null ? updatedData.description : originalEMeal.description)
				.withImageThumb(updatedData.imageThumb != null ? updatedData.imageThumb : originalEMeal.imageThumb)
				.build();

		if(updatedData.slots != null) {
			updatedData.slots.stream().forEach((slot) -> { eMeal.addSlot(ESlotMapper.toEntity(slot)); });
		}

		return eMeal;
	}

	public void deleteMeal(Integer mealId) {
		if(mealId == null) {
			throw new IllegalArgumentException("Cannot delete null id.");
		}
		this.mealsRepository.deleteById(mealId);
	}

}

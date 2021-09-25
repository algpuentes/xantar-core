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

	public Meal updateMeal(Meal updatedMeal) {
		if(updatedMeal == null || updatedMeal.id == null) {
			throw new IllegalArgumentException("Cannot update null value as meal");
		}
		final EMeal originalEMeal = this.mealsRepository.findById(updatedMeal.id)
				.orElseThrow(() -> new EntityNotFoundException("Meal with id ''" + updatedMeal.id));

		return EMealMapper.toModel(this.mealsRepository.save(this.updateEntity(originalEMeal, EMealMapper.toEntity(updatedMeal))));
	}

	/**
	 * Updates just non null values
	 * **/
	private EMeal updateEntity(EMeal originalEMeal, EMeal updatedEMeal) {
		return new EMeal.EMealBuilder()
				.withId(originalEMeal.id)
				.withName(updatedEMeal.name != null ? updatedEMeal.name : originalEMeal.name)
				.withDescription(updatedEMeal.description  != null ? updatedEMeal.description : originalEMeal.description)
				.withSlots(updatedEMeal.slots != null ? updatedEMeal.slots : originalEMeal.slots)
				.withImageThumb(updatedEMeal.imageThumb != null ? updatedEMeal.imageThumb : originalEMeal.imageThumb)
				.build();
	}

	public void deleteMeal(Integer mealId) {
		if(mealId == null) {
			throw new IllegalArgumentException("Cannot delete null id.");
		}
		this.mealsRepository.deleteById(mealId);
	}

}

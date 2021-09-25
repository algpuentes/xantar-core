package com.xantar.xantarcore.db;

import java.util.List;
import java.util.stream.Collectors;

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

	public void deleteMeal(Integer mealId) {
		if(mealId == null) {
			throw new IllegalArgumentException("Cannot delete null id.");
		}
		this.mealsRepository.deleteById(mealId);
	}

}

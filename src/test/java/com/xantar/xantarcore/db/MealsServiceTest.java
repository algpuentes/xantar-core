package com.xantar.xantarcore.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.xantar.xantarcore.models.Meal;

public class MealsServiceTest {

	MealsService mealsService;
	IMealsRepository mealsRepositoryMock = Mockito.mock(IMealsRepository.class);


	/*
	 *  findAll()
	 * */
	@Test
	void findAll_returnsNonEmtpyList() {
		this.mealsService = new MealsService(this.mealsRepositoryMock);
		Mockito.when(this.mealsRepositoryMock.findAll()).thenReturn(Collections.singletonList(Mockito.mock(EMeal.class)));

		final List<Meal> mealsList = this.mealsService.findAll();

		Mockito.verify(this.mealsRepositoryMock).findAll();
		assertNotNull(mealsList);
		assertEquals(1, mealsList.size());
		assertEquals(Meal.class, mealsList.get(0).getClass());
	}

	/*
	 * createMeal(Meal)
	 * */
	@Test
	void createMeal_notNullArgument_savesMealInDB() {
		this.mealsService = new MealsService(this.mealsRepositoryMock);
		Mockito.when(this.mealsRepositoryMock.save(Mockito.any(EMeal.class))).thenReturn(Mockito.mock(EMeal.class));

		this.mealsService.createMeal(Mockito.mock(Meal.class));

		Mockito.verify(this.mealsRepositoryMock).save(Mockito.any(EMeal.class));
	}

	@Test
	void createMeal_nullArgument_throwsException() {
		this.mealsService = new MealsService(this.mealsRepositoryMock);

		assertThrows(IllegalArgumentException.class, () -> this.mealsService.createMeal(null));
	}

}

package com.xantar.xantarcore.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.xantar.xantarcore.models.Meal;

public class MealsServiceTest {

	MealsService mealsService;
	IMealsRepository mealsRepository = Mockito.mock(IMealsRepository.class);

	@Test
	void findAll_returnsNonEmtpyList() {
		this.mealsService = new MealsService(this.mealsRepository);
		Mockito.when(this.mealsRepository.findAll()).thenReturn(Collections.singletonList(Mockito.mock(EMeal.class)));

		final List<Meal> mealsList = this.mealsService.findAll();

		Mockito.verify(this.mealsRepository).findAll();
		assertNotNull(mealsList);
		assertEquals(1, mealsList.size());
		assertEquals(Meal.class, mealsList.get(0).getClass());
	}

}

package com.xantar.xantarcore.meals;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;

import com.xantar.xantarcore.db.MealsService;
import com.xantar.xantarcore.models.Meal;

public class MealsControllerTest {

	static MealsController mealsController;

	static MealsService mealService;


	@BeforeAll
	static void initializeService() {
		mealService = Mockito.mock(MealsService.class);
		mealsController = new MealsController(mealService);
	}

	/*
	 * constructor
	 * */
	@Test
	void constructor_withNullArgument_shouldThrowException() {
		assertThrows(IllegalArgumentException.class, () -> new MealsService(null));
	}

	/*
	 * findMeal(Integer)
	 * */
	@Test
	void findMeal_withExistingMeal_shouldReturnMeal() {
		final var expectedMeal = new Meal.MealBuilder()
				.withId(1)
				.withName("name")
				.build();

		Mockito.when(mealService.findById(expectedMeal.id)).thenReturn(expectedMeal);

		final var responseEntity = mealsController.findMeal(expectedMeal.id);

		Mockito.verify(mealService).findById(expectedMeal.id);
		assertEquals( HttpStatus.OK, responseEntity.getStatusCode(), "Response http status should be OK");
		assertEquals(MealResponseMapper.toJsonResponse(expectedMeal), responseEntity.getBody(), "Should return expected meal");
	}

	/*
	 * findAllMeals()
	 * */
	@Test
	void findAllMeals_withExistingMeals_shouldReturnListOfMeals() {
		final var expectedMeal = new Meal.MealBuilder()
				.withId(1)
				.withName("name")
				.build();

		Mockito.when(mealService.findAll()).thenReturn(Collections.singletonList(expectedMeal));

		final var responseEntity = mealsController.findAllMeals();

		Mockito.verify(mealService).findById(expectedMeal.id);
		assertEquals( HttpStatus.OK, responseEntity.getStatusCode(), "Response http status should be OK");
		assertTrue(responseEntity.getBody().size() == 1);
	}


	/*
	 * createMeal(MealResponseJson)
	 * */
	@Test
	void createMeal_withNonExistingMeal_shouldReturnCreatedMeal() {
		final var toCreateMeal = new Meal.MealBuilder()
				.withName("name")
				.build();
		final var expectedMeal = new Meal.MealBuilder()
				.withId(1)
				.withName("name")
				.build();

		Mockito.when(mealService.createMeal(toCreateMeal)).thenReturn(expectedMeal);

		final var responseEntity = mealsController.createMeal(MealResponseMapper.toJsonResponse(toCreateMeal));

		Mockito.verify(mealService).createMeal(Mockito.any(Meal.class));
		assertEquals( HttpStatus.CREATED, responseEntity.getStatusCode(), "Response http status should be CREATED");
	}

	/*
	 * updateMeal(MealResponseJson)
	 * */
	@Test
	void updateMeal_withExistingMeal_shouldReturnUpdatedMeal() {
		final var toUpateMeal = new Meal.MealBuilder()
				.withName("name_changed")
				.build();
		final var expectedMeal = new Meal.MealBuilder()
				.withId(1)
				.withName("original_name")
				.withDescription("original_desc")
				.build();

		Mockito.when(mealService.updateMeal(toUpateMeal)).thenReturn(expectedMeal);

		final var responseEntity = mealsController.updateMeal(1, MealResponseMapper.toJsonResponse(toUpateMeal));

		Mockito.verify(mealService).updateMeal(Mockito.any(Meal.class));
		assertEquals( HttpStatus.OK, responseEntity.getStatusCode(), "Response http status should be OK");
	}

	/*
	 * deleteMeal(int)
	 * */
	@Test
	void deleteMeal_withExistingMeal_shouldDeleteMeal() {

		final var responseEntity = mealsController.deleteMeal(1);

		Mockito.verify(mealService).deleteMeal(1);
		assertEquals( HttpStatus.OK, responseEntity.getStatusCode(), "Response http status should be OK");
	}

}

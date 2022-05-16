package com.xantar.xantarcore.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class MealsServiceTest {

	MealsService mealsService;
	IMealsRepository mealsRepositoryMock = Mockito.mock(IMealsRepository.class);

	/*
	 * MealsService(IMealsRepository)
	 * */
	@Test
	void constructor_withNotNullRepo_returnsCorrectInstance() {
		final var service = new MealsService(this.mealsRepositoryMock);
		assertNotNull(service);
	}

	@Test
	void constructor_withNullRepo_throwsException() {
		assertThrows(IllegalArgumentException.class, () -> new MealsService(null));
	}

	/*
	 * findById()
	 * */
	@Test
	void findOne_withExistingMeal_returnsCorrectMeal() {
		this.mealsService = new MealsService(this.mealsRepositoryMock);

		final var expectedMeal = Meal.builder().withId(1).withName("name").withSlots(new ArrayList<Slot>()).build();
		Mockito.when(this.mealsRepositoryMock.findById(expectedMeal.id)).thenReturn(Optional.of(new EMeal(expectedMeal)));

		final var resultMeal = this.mealsService.findById(expectedMeal.id);

		Mockito.verify(this.mealsRepositoryMock).findById(expectedMeal.id);
		assertEquals(expectedMeal, resultMeal);
	}

	@Test
	void findOne_withNoExistingMeal_throwsException() {
		this.mealsService = new MealsService(this.mealsRepositoryMock);

		final int id = 1;
		assertThrows(EntityNotFoundException.class, () -> this.mealsService.findById(id));
		Mockito.verify(this.mealsRepositoryMock).findById(id);
	}

	/*
	 *  findAll()
	 * */
	@Test
	void findAll_returnsNonEmtpyList() {
		this.mealsService = new MealsService(this.mealsRepositoryMock);
		Mockito.when(this.mealsRepositoryMock.findAll()).thenReturn(Collections.singletonList(Mockito.mock(EMeal.class)));

		final var mealsList = this.mealsService.findAll();

		Mockito.verify(this.mealsRepositoryMock).findAll();
		assertNotNull(mealsList);
		assertEquals(1, mealsList.size());
		assertEquals(Meal.class, mealsList.get(0).getClass());
	}

	void findAll_returnsEmtpyList() {
		this.mealsService = new MealsService(this.mealsRepositoryMock);

		final var mealsList = this.mealsService.findAll();

		Mockito.verify(this.mealsRepositoryMock).findAll();
		assertNotNull(mealsList);
		assertTrue(mealsList.isEmpty());
	}

	/*
	 * createMeal(Meal)
	 * */
	@Test
	void createMeal_notNullArgument_savesMealInDB() {
		this.mealsService = new MealsService(this.mealsRepositoryMock);
		final var expectedMeal = Meal.builder().withId(1).withName("name").withSlots(new ArrayList<Slot>()).build();

		Mockito.when(this.mealsRepositoryMock.save(Mockito.any(EMeal.class))).thenReturn(new EMeal(expectedMeal));

		final var resultMeal = this.mealsService.createMeal(Meal.builder().withName("name").build());

		Mockito.verify(this.mealsRepositoryMock).save(Mockito.any(EMeal.class));
		assertEquals(expectedMeal, resultMeal);
	}

	@Test
	void createMeal_nullArgument_throwsException() {
		this.mealsService = new MealsService(this.mealsRepositoryMock);

		assertThrows(IllegalArgumentException.class, () -> this.mealsService.createMeal(null));
	}

	@Test
	void createMeal_nullName_throwsException() {
		this.mealsService = new MealsService(this.mealsRepositoryMock);

		assertThrows(IllegalArgumentException.class, () -> this.mealsService.createMeal(Mockito.mock(Meal.class)));
	}

	/*
	 * updateMeal(Meal)
	 * */
	@Test
	void updateMeal_nullArgument_throwsException() {
		this.mealsService = new MealsService(this.mealsRepositoryMock);

		assertThrows(IllegalArgumentException.class, () -> this.mealsService.updateMeal(null));
	}

	@Test
	void updateMeal_nullId_throwsException() {
		this.mealsService = new MealsService(this.mealsRepositoryMock);

		assertThrows(IllegalArgumentException.class, () -> this.mealsService.updateMeal(Meal.builder().build()));
	}

	@Test
	void updateMeal_withExistingMeal_shouldUpdateValues() {
		this.mealsService = new MealsService(this.mealsRepositoryMock);
		final var dataToUpdate = Meal.builder()
				.withId(1)
				.withName("name_changed")
				.withDescription("description_changed")
				.withImageThumb("image_changed".getBytes())
				.withSlots(Collections.singletonList(new Slot(1, "slot")))
				.build();
		final var expectedMeal = Meal.builder()
				.withId(1)
				.withName("name_changed")
				.withDescription("description_changed")
				.withImageThumb("image_changed".getBytes())
				.withSlots(Collections.singletonList(new Slot(1, "slot")))
				.build();
		final var existingMeal = EMeal.builder()
				.withId(1)
				.withName("name")
				.withDescription("description")
				.withImageThumb("image".getBytes())
				.withSlots(new HashSet<ESlot>())
				.build();

		Mockito.when(this.mealsRepositoryMock.findById(1)).thenReturn(Optional.of(existingMeal));
		Mockito.when(this.mealsRepositoryMock.save(new EMeal(expectedMeal))).thenReturn(new EMeal(expectedMeal));


		final var resultMeal = this.mealsService.updateMeal(dataToUpdate);

		Mockito.verify(this.mealsRepositoryMock).findById(dataToUpdate.id);
		Mockito.verify(this.mealsRepositoryMock).save(new EMeal(expectedMeal));
		assertEquals(expectedMeal, resultMeal);
	}

	@Test
	void updateMeal_withExistingMeal_shouldUpdateNoValues() {
		this.mealsService = new MealsService(this.mealsRepositoryMock);
		final var slot = new Slot(1, "slot");
		final var dataToUpdate = Meal.builder()
				.withId(1)
				.build();
		final var expectedMeal = Meal.builder()
				.withId(1)
				.withName("name")
				.withDescription("description")
				.withImageThumb("image".getBytes())
				.withSlots(Collections.singletonList(slot))
				.build();
		final var existingMeal = EMeal.builder()
				.withId(1)
				.withName("name")
				.withDescription("description")
				.withImageThumb("image".getBytes())
				.withSlots(Set.of(new ESlot(slot)))
				.build();

		Mockito.when(this.mealsRepositoryMock.findById(1)).thenReturn(Optional.of(existingMeal));
		Mockito.when(this.mealsRepositoryMock.save(new EMeal(expectedMeal))).thenReturn(new EMeal(expectedMeal));


		final var resultMeal = this.mealsService.updateMeal(dataToUpdate);

		Mockito.verify(this.mealsRepositoryMock).findById(dataToUpdate.id);
		Mockito.verify(this.mealsRepositoryMock).save(new EMeal(expectedMeal));
		assertEquals(expectedMeal, resultMeal);
	}

	@Test
	void updateMeal_withNonExistingMeal_shouldThrowException() {
		this.mealsService = new MealsService(this.mealsRepositoryMock);
		final var dataToUpdate = Meal.builder()
				.withId(1)
				.withDescription("description_changed")
				.build();

		assertThrows(EntityNotFoundException.class, () -> this.mealsService.updateMeal(dataToUpdate));
		Mockito.verify(this.mealsRepositoryMock).findById(dataToUpdate.id);
		Mockito.verify(this.mealsRepositoryMock, Mockito.never()).save(Mockito.any(EMeal.class));
	}

	@Test
	void updateMeal_withEmptyName_shouldThrowException() {
		this.mealsService = new MealsService(this.mealsRepositoryMock);
		final var dataToUpdate = Meal.builder()
				.withId(1)
				.withName(" ")
				.withDescription("description_changed")
				.build();

		final var existingMeal = EMeal.builder()
				.withId(1)
				.withName("name")
				.build();

		Mockito.when(this.mealsRepositoryMock.findById(1)).thenReturn(Optional.of(existingMeal));

		assertThrows(IllegalArgumentException.class, () -> this.mealsService.updateMeal(dataToUpdate));
		Mockito.verify(this.mealsRepositoryMock).findById(dataToUpdate.id);
		Mockito.verify(this.mealsRepositoryMock, Mockito.never()).save(Mockito.any(EMeal.class));
	}

	/*
	 * deleteMeal(Integer)
	 * */
	@Test
	void deleteMeal_shouldCallDeleteInRepo() {
		this.mealsService = new MealsService(this.mealsRepositoryMock);

		final int id = 1;
		this.mealsService.deleteMeal(id);

		Mockito.verify(this.mealsRepositoryMock).deleteById(id);
	}
}

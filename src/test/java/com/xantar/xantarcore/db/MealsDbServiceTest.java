package com.xantar.xantarcore.db;

import com.xantar.xantarcore.meals.Meal;
import com.xantar.xantarcore.meals.Slot;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class MealsDbServiceTest {

	MealsDbService mealsDbService;
	IMealsRepository mealsRepositoryMock = Mockito.mock(IMealsRepository.class);

	/*
	 * MealsService(IMealsRepository)
	 * */
	@Test
	void constructor_withNotNullRepo_returnsCorrectInstance() {
		var service = new MealsDbService(this.mealsRepositoryMock);
		assertNotNull(service);
	}

	@Test
	void constructor_withNullRepo_throwsException() {
		assertThrows(NullPointerException.class, () -> new MealsDbService(null));
	}

	/*
	 * findById()
	 * */
	@Test
	void findOne_withExistingMeal_returnsCorrectMeal() {
		this.mealsDbService = new MealsDbService(this.mealsRepositoryMock);

		var expectedMeal = Meal.builder().withId(1).withName("name").withSlots(new ArrayList<>()).build();
		Mockito.when(this.mealsRepositoryMock.findById(expectedMeal.id())).thenReturn(Optional.of(new EMeal(expectedMeal)));

		var resultMeal = this.mealsDbService.findById(expectedMeal.id());

		Mockito.verify(this.mealsRepositoryMock).findById(expectedMeal.id());
		assertTrue(resultMeal.isPresent());
		assertEquals(expectedMeal, resultMeal.get());
	}

	@Test
	void findOne_withNoExistingMeal_throwsException() {
		this.mealsDbService = new MealsDbService(this.mealsRepositoryMock);
		final int id = 1;
		Mockito.when(this.mealsRepositoryMock.findById(id)).thenReturn(Optional.empty());

		var resultMeal = this.mealsDbService.findById(id);

		Mockito.verify(this.mealsRepositoryMock).findById(id);
		assertTrue(resultMeal.isEmpty());
	}

	/*
	 *  findAll()
	 * */
	@Test
	void findAll_returnsNonEmptyList() {
		this.mealsDbService = new MealsDbService(this.mealsRepositoryMock);
		var eMeal = new EMeal();
		eMeal.name = "name";
		Mockito.when(this.mealsRepositoryMock.findAll()).thenReturn(Collections.singletonList(eMeal));

		var mealsList = this.mealsDbService.findAll();

		Mockito.verify(this.mealsRepositoryMock).findAll();
		assertNotNull(mealsList);
		assertEquals(1, mealsList.size());
		assertEquals(Meal.class, mealsList.get(0).getClass());
	}

	void findAll_returnsEmptyList() {
		this.mealsDbService = new MealsDbService(this.mealsRepositoryMock);

		var mealsList = this.mealsDbService.findAll();

		Mockito.verify(this.mealsRepositoryMock).findAll();
		assertNotNull(mealsList);
		assertTrue(mealsList.isEmpty());
	}

	/*
	 * createMeal(Meal)
	 * */
	@Test
	void createMeal_notNullArgument_savesMealInDB() {
		this.mealsDbService = new MealsDbService(this.mealsRepositoryMock);
		var expectedMeal = Meal.builder().withId(1).withName("name").withSlots(new ArrayList<Slot>()).build();

		Mockito.when(this.mealsRepositoryMock.save(Mockito.any(EMeal.class))).thenReturn(new EMeal(expectedMeal));

		var resultMeal = this.mealsDbService.createMeal(Meal.builder().withName("name").build());

		Mockito.verify(this.mealsRepositoryMock).save(Mockito.any(EMeal.class));
		assertEquals(expectedMeal, resultMeal);
	}

	@Test
	void createMeal_nullArgument_throwsException() {
		this.mealsDbService = new MealsDbService(this.mealsRepositoryMock);

		assertThrows(NullPointerException.class, () -> this.mealsDbService.createMeal(null));
	}

	/*
	 * updateMeal(Meal)
	 * */
	@Test
	void updateMeal_nullArgument_throwsException() {
		this.mealsDbService = new MealsDbService(this.mealsRepositoryMock);

		assertThrows(IllegalArgumentException.class, () -> this.mealsDbService.updateMeal(null));
	}

	@Test
	void updateMeal_nullId_throwsException() {
		this.mealsDbService = new MealsDbService(this.mealsRepositoryMock);

		assertThrows(IllegalArgumentException.class, () -> this.mealsDbService.updateMeal(Meal.builder().build()));
	}

	@Test
	void updateMeal_withExistingMeal_shouldUpdateValues() {
		this.mealsDbService = new MealsDbService(this.mealsRepositoryMock);
		var dataToUpdate = Meal.builder()
				.withId(1)
				.withName("name_changed")
				.withDescription("description_changed")
				.withImageThumb("image_changed".getBytes())
				.withSlots(Collections.singletonList(new Slot(1, "slot")))
				.build();
		var expectedMeal = Meal.builder()
				.withId(1)
				.withName("name_changed")
				.withDescription("description_changed")
				.withImageThumb("image_changed".getBytes())
				.withSlots(Collections.singletonList(new Slot(1, "slot")))
				.build();

		var existingEMeal = new EMeal();
		existingEMeal.id = 1;
		existingEMeal.name = "name";
		existingEMeal.description = "description";
		existingEMeal.imageThumb = "image".getBytes();
		existingEMeal.slots = new HashSet<>();

		Mockito.when(this.mealsRepositoryMock.findById(1)).thenReturn(Optional.of(existingEMeal));
		Mockito.when(this.mealsRepositoryMock.save(new EMeal(expectedMeal))).thenReturn(new EMeal(expectedMeal));


		var resultMeal = this.mealsDbService.updateMeal(dataToUpdate);

		Mockito.verify(this.mealsRepositoryMock).findById(dataToUpdate.id());
		Mockito.verify(this.mealsRepositoryMock).save(new EMeal(expectedMeal));
		assertEquals(expectedMeal, resultMeal);
	}

	@Test
	void updateMeal_withExistingMeal_shouldUpdateNoValues() {
		this.mealsDbService = new MealsDbService(this.mealsRepositoryMock);
		var slot = new Slot(1, "slot");
		var dataToUpdate = Meal.builder()
				.withId(1)
				.withName("name")
				.build();
		var expectedMeal = Meal.builder()
				.withId(1)
				.withName("name")
				.withDescription("description")
				.withImageThumb("image".getBytes())
				.withSlots(Collections.singletonList(slot))
				.build();
		var existingEMeal = new EMeal();
		existingEMeal.id = 1;
		existingEMeal.name = "name";
		existingEMeal.description = "description";
		existingEMeal.imageThumb = "image".getBytes();
		existingEMeal.slots = Set.of(new ESlot(slot));

		Mockito.when(this.mealsRepositoryMock.findById(1)).thenReturn(Optional.of(existingEMeal));
		Mockito.when(this.mealsRepositoryMock.save(new EMeal(expectedMeal))).thenReturn(new EMeal(expectedMeal));


		var resultMeal = this.mealsDbService.updateMeal(dataToUpdate);

		Mockito.verify(this.mealsRepositoryMock).findById(dataToUpdate.id());
		Mockito.verify(this.mealsRepositoryMock).save(new EMeal(expectedMeal));
		assertEquals(expectedMeal, resultMeal);
	}

	@Test
	void updateMeal_withNonExistingMeal_shouldThrowException() {
		this.mealsDbService = new MealsDbService(this.mealsRepositoryMock);
		var dataToUpdate = Meal.builder()
				.withId(1)
				.withName("name")
				.withDescription("description_changed")
				.build();

		assertThrows(RuntimeException.class, () -> this.mealsDbService.updateMeal(dataToUpdate));
		Mockito.verify(this.mealsRepositoryMock).findById(dataToUpdate.id());
		Mockito.verify(this.mealsRepositoryMock, Mockito.never()).save(Mockito.any(EMeal.class));
	}

	/*
	 * deleteMeal(Integer)
	 * */
	@Test
	void deleteMeal_shouldCallDeleteInRepo() {
		this.mealsDbService = new MealsDbService(this.mealsRepositoryMock);

		final int id = 1;
		this.mealsDbService.deleteMeal(id);

		Mockito.verify(this.mealsRepositoryMock).deleteById(id);
	}
}

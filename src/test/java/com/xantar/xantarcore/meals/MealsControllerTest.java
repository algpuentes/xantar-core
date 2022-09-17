package com.xantar.xantarcore.meals;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

import com.xantar.xantarcore.db.MealsDbService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xantar.xantarcore.common.utils.Base64Utils;


@WebMvcTest(MealsController.class)
class MealsControllerTest {

	private static final String MEAL_IMAGE_STRING = "YmFzaWM=";

	private static final String MEAL_DESCRIPTION = "description";

	private static final String MEAL_NAME = "name";

	private static final String ENDPOINT_API_MEALS = "/api/meals";

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	static MealsController mealsController;

	@MockBean
	static MealsDbService mealService;

	/*
	 * constructor
	 * */
	@Test
	void constructor_withNullArgument_shouldThrowException() {
		assertThrows(NullPointerException.class, () -> new MealsDbService(null));
	}

	/**
	 *
	 * {
	 * 	"id": 1234,
	 * 	"name": "Name Surname",
	 * 	"description": "Description of the meal",
	 * 	"imageThumb": "string containing the image",
	 * 	"slots": [{
	 * 		"id": 1234,
	 * 		"name": "Slot name"
	 *	 }]
	 * }
	 * */

	/*
	 * findMeal(Integer)
	 * */
	@Test
	void findMeal_withExistingMeal_shouldReturnMeal() throws Exception {
		var requestedId = 1;
		var expectedMeal = Meal.builder()
				.withId(requestedId)
				.withName(MEAL_NAME)
				.withDescription(MEAL_DESCRIPTION)
				.withImageThumb(Base64Utils.decodeImage(MEAL_IMAGE_STRING))
				.build();

		Mockito.when(mealService.findById(expectedMeal.id())).thenReturn(Optional.of(expectedMeal));

		this.mockMvc.perform(get(MealsControllerTest.ENDPOINT_API_MEALS + "/" + requestedId))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id").value(requestedId))
		.andExpect(jsonPath("$.name").value(expectedMeal.name()))
		.andExpect(jsonPath("$.description").value(expectedMeal.description()))
		.andExpect(jsonPath("$.slots").isEmpty())
		.andExpect(jsonPath("$.imageThumb").value(Base64Utils.encodeImage(expectedMeal.imageThumb())));
	}

	@Test
	void findMeal_withNonExistingMeal_shouldReturnNotFoundError() throws Exception {
		var requestedId = 1;

		this.mockMvc.perform(get(MealsControllerTest.ENDPOINT_API_MEALS + "/" + requestedId))
		.andDo(print())
		.andExpect(status().isNotFound());
	}

	/*
	 * findAllMeals()
	 * */
	@Test
	void findAllMeals_withExistingMeals_shouldReturnListOfMeals() throws Exception {
		var expectedMeal = Meal.builder()
				.withId(1)
				.withName(MEAL_NAME)
				.withDescription(MEAL_DESCRIPTION)
				.withImageThumb(Base64Utils.decodeImage(MEAL_IMAGE_STRING))
				.build();

		Mockito.when(mealService.findAll()).thenReturn(Collections.singletonList(expectedMeal));

		this.mockMvc.perform(get(MealsControllerTest.ENDPOINT_API_MEALS))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(1)))
		.andExpect(jsonPath("$[0].id").value(expectedMeal.id()))
		.andExpect(jsonPath("$[0].name").value(expectedMeal.name()))
		.andExpect(jsonPath("$[0].description").value(expectedMeal.description()))
		.andExpect(jsonPath("$[0].slots").isEmpty())
		.andExpect(jsonPath("$[0].imageThumb").value(Base64Utils.encodeImage(expectedMeal.imageThumb())));
	}


	/*
	 * createMeal(MealResponseJson)
	 * */
	@Test
	void createMeal_withNonExistingMeal_shouldReturnCreatedMeal() throws Exception {
		var requestedJsonMeal = MealResponseJson.builder()
				.withName(MEAL_NAME)
				.withDescription(MEAL_DESCRIPTION)
				.withImageThumb(MEAL_IMAGE_STRING)
				.build();
		var requestedMeal = Meal.builder()
				.withName(MEAL_NAME)
				.withDescription(MEAL_DESCRIPTION)
				.withImageThumb(Base64Utils.decodeImage(MEAL_IMAGE_STRING))
				.build();

		var createdId = 1;
		var createdMeal = Meal.builder()
				.withId(createdId)
				.withName(MEAL_NAME)
				.withDescription(MEAL_DESCRIPTION)
				.withImageThumb(Base64Utils.decodeImage(MEAL_IMAGE_STRING))
				.build();

		Mockito.when(mealService.createMeal(requestedMeal)).thenReturn(createdMeal);

		this.mockMvc.perform(post(MealsControllerTest.ENDPOINT_API_MEALS)
				.content(asJsonString(requestedJsonMeal))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.id").value(createdId))
		.andExpect(jsonPath("$.name").value(requestedJsonMeal.name()))
		.andExpect(jsonPath("$.description").value(requestedJsonMeal.description()))
		.andExpect(jsonPath("$.slots").isEmpty())
		.andExpect(jsonPath("$.imageThumb").value(requestedJsonMeal.imageThumb()));

		Mockito.verify(mealService).createMeal(Mockito.any(Meal.class));
	}

	/*
	 * updateMeal(MealResponseJson)
	 * */
	@Test
	void updateMeal_withExistingMeal_shouldReturnUpdatedMeal() throws Exception {
		var existingId = 1;
		var requestedJsonMeal = MealResponseJson.builder()
				.withName("modified_name")
				.withDescription("modified_description")
				.withImageThumb("YMFzaWM=")
				.build();
		var updatedMeal = Meal.builder()
				.withId(existingId)
				.withName(requestedJsonMeal.name())
				.withDescription(requestedJsonMeal.description())
				.withImageThumb(Base64Utils.decodeImage(requestedJsonMeal.imageThumb()))
				.build();

		Mockito.when(mealService.updateMeal(updatedMeal)).thenReturn(updatedMeal);

		this.mockMvc.perform(patch(MealsControllerTest.ENDPOINT_API_MEALS + "/" + existingId)
				.content(asJsonString(requestedJsonMeal))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id").value(existingId))
		.andExpect(jsonPath("$.name").value(requestedJsonMeal.name()))
		.andExpect(jsonPath("$.description").value(requestedJsonMeal.description()))
		.andExpect(jsonPath("$.slots").isEmpty())
		.andExpect(jsonPath("$.imageThumb").value(requestedJsonMeal.imageThumb()));

		Mockito.verify(mealService).updateMeal(Mockito.any(Meal.class));
	}

	/*
	 * deleteMeal(int)
	 * */
	@Test
	void deleteMeal_withExistingMeal_shouldDeleteMeal() throws Exception {

		var toDeleteId = 1;

		this.mockMvc.perform(delete(MealsControllerTest.ENDPOINT_API_MEALS + "/" + toDeleteId)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isOk());

		Mockito.verify(mealService).deleteMeal(1);
	}

	private MealResponseJson toJsonResponse(Meal meal) {
		return MealResponseJson.builder()
				.withId(meal.id())
				.withName(meal.name())
				.withDescription(meal.description())
				.withImageThumb(Base64Utils.encodeImage(meal.imageThumb()))
				.withSlots(Optional.ofNullable(meal.slots()).map(r -> r.stream().map(slot -> new SlotResponseJson(slot.id(), slot.name())).collect(Collectors.toList()))
						.orElse(new ArrayList<>()))
				.build();
	}


	private static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

}

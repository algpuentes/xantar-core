package com.xantar.xantarcore.schedule;

import java.util.*;
import java.util.stream.Collectors;

import com.xantar.xantarcore.common.exceptions.ScheduleProcessingException;
import com.xantar.xantarcore.meals.Meal;
import com.xantar.xantarcore.db.MealsDbService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ScheduleService {

	private static final Logger LOGGER	= LoggerFactory.getLogger(ScheduleService.class);

	private final MealsDbService mealsDbService;

	@Autowired
	public ScheduleService(MealsDbService mealsDbService) {
		this.mealsDbService = Objects.requireNonNull(mealsDbService, "Meals repository must not be null");
	}

	public List<DaySchedule> generateSchedule(List<DaySchedule> scheduleRequest) {
		var mealsMap = retrieveMealsForConfiguration(scheduleRequest);
		return assignMealsToSchedule(scheduleRequest, mealsMap);
	}

	private LinkedList<DaySchedule> assignMealsToSchedule(List<DaySchedule> scheduleRequest, Map<Integer, Stack<Meal>> mealsMap) {
		var schedule = new LinkedList<DaySchedule>();

		scheduleRequest.forEach(day ->
			schedule.add(new DaySchedule(day.id(), day.timestamp(), assignSlotSchedules(mealsMap, day)))
		);

		return schedule;
	}

	private LinkedList<SlotSchedule> assignSlotSchedules(Map<Integer, Stack<Meal>> mealsMap, DaySchedule day) {
		var scheduleConfigList = new LinkedList<SlotSchedule>();
		day.configurations().forEach(config ->
			scheduleConfigList.add(new SlotSchedule(config.slot(), mealsMap.get(config.slot().id()).pop()))
		);
		return scheduleConfigList;
	}

	private Map<Integer, Stack<Meal>> retrieveMealsForConfiguration(List<DaySchedule> daysList) {
		var mealsMap = new HashMap<Integer, Stack<Meal>>();

		Optional.ofNullable(daysList)
				.filter(list -> !list.isEmpty())
				.orElseThrow(() -> new IllegalArgumentException("Cannot generate schedule for empty requested configuration"))
				.stream()
				.flatMap(day -> day.configurations().stream())
				.collect(Collectors.groupingBy(day -> day.slot().id()))
				.forEach((slotId, configurations) ->
					mealsMap.put(slotId, findMealsForSlot(slotId, configurations.size()))
				);
		return mealsMap;
	}

	private Stack<Meal> findMealsForSlot(Integer slotId, int numberOfMeals) {
		var dbMeals = this.mealsDbService.findMealsBySlotAndMaxRows(slotId, numberOfMeals);
		if(dbMeals.isEmpty()) {
			throw new ScheduleProcessingException("No meal available for slot with id ["+ slotId +"]");
		}

		return buildMealsStack(numberOfMeals, dbMeals);
	}

	private Stack<Meal> buildMealsStack(int numberOfMeals, List<Meal> dbMeals) {
		var mealsStack = new Stack<Meal>();
		int dbMealsIndex = 0;
		while(mealsStack.size() < numberOfMeals) {
			//Ensure the meals stack contains enough items to fill requested slots
			if(dbMealsIndex == dbMeals.size()) {
				dbMealsIndex = 0;
			}
			mealsStack.add(dbMeals.get(dbMealsIndex));
			dbMealsIndex++;
		}
		return mealsStack;
	}

}

package com.xantar.xantarcore.db;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ScheduleService {

	private final Logger LOGGER	= LoggerFactory.getLogger(ScheduleService.class);

	private final MealsService	mealsService;

	@Autowired
	public ScheduleService(MealsService mealsService) {
		if (mealsService == null) {
			throw new IllegalArgumentException("Meals repository must not be null");
		}

		this.mealsService = mealsService;
	}


	public List<ScheduleDay> generateSchedule(List<ScheduleDay> daysList) {
		final Map<Integer, Stack<Meal>> mealsMap = new HashMap<>();

		daysList.stream()
		.flatMap(day ->  day.configurations.stream())
		.collect(Collectors.groupingBy(day -> day.slot.id))
		.forEach((slotId, configurations) -> {
			final var dbMeals = this.mealsService.findMealsBySlotAndMaxRows(slotId, configurations.size());
			if(dbMeals.size() < 1) {
				throw new RuntimeException("No meal available for slot with id ["+ slotId +"]");
			}

			final var mealsStack = new Stack<Meal>();
			while(mealsStack.size() < configurations.size()) {
				//Ensure the meals stack contains enough items to fill requested slots
				mealsStack.add(dbMeals.get(configurations.size() - dbMeals.size()));
			}
			mealsMap.put(slotId, mealsStack);
		});

		final var schedule = new LinkedList<ScheduleDay>();

		daysList.stream()
		.forEach(day -> {
			final ScheduleDay scheduledDay = new ScheduleDay(day.id, day.timestamp, new LinkedList<ScheduleConfiguration>());
			day.configurations.forEach(config -> {
				scheduledDay.configurations.add(new ScheduleConfiguration(config.slot, mealsMap.get(config.slot.id).pop()));

			});
			schedule.add(scheduledDay);
		});

		return schedule;


	}

}

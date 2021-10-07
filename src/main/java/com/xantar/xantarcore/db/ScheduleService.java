package com.xantar.xantarcore.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xantar.xantarcore.models.ScheduleConfiguration;
import com.xantar.xantarcore.models.ScheduleDay;

@Component
public class ScheduleService {

	private final Logger LOGGER	= LoggerFactory.getLogger(ScheduleService.class);

	private final IMealsRepository	mealsRepository;

	@Autowired
	public ScheduleService(IMealsRepository mealsRepository) {
		if (mealsRepository == null) {
			throw new IllegalArgumentException("Meals repository must not be null");
		}

		this.mealsRepository = mealsRepository;
	}


	public List<ScheduleDay> generateSchedule(List<ScheduleDay> daysList) {
		final Map<Integer, List<EMeal>> eMealsMap = new HashMap<>();

		daysList.stream()
		.flatMap(day ->  day.configurations.stream())
		.collect(Collectors.groupingBy(ScheduleConfiguration::retrieveSlotId))
		.forEach((slotId, configurations) -> {
			eMealsMap.put(slotId, this.mealsRepository.findMealsBySlotAndMaxRows(slotId, configurations.size()));
		});


		daysList.stream()
		.forEach( day -> day.configurations
				.forEach(config -> {
					if(eMealsMap.get(config.retrieveSlotId()).size() > 0) {
						final int index = (int) (Math.random() * (eMealsMap.get(config.retrieveSlotId()).size() - 0));
						config.meal = EMealMapper.toModel(
								eMealsMap.get(config.retrieveSlotId()).get(index));
					}
				}));

		return daysList;
	}

}

package com.xantar.xantarcore.schedule;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xantar.xantarcore.db.ScheduleService;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

	Logger	LOGGER = LoggerFactory.getLogger(ScheduleController.class);

	private final ScheduleService	scheduleService;

	@Autowired
	ScheduleController(ScheduleService scheduleService) {
		if (scheduleService == null) {
			throw new IllegalArgumentException("Schedule service must not be null");
		}

		this.scheduleService = scheduleService;
	}


	@PostMapping()
	public ResponseEntity<List<ScheduleDayJson>> createMeal(@RequestBody List<ScheduleDayJson> jScheduleRequest) {
		final var scheduleList = jScheduleRequest.stream().map(ScheduleDayJson::toScheduleDay).collect(Collectors.toList());
		final List<ScheduleDayJson> jSchedule = this.scheduleService.generateSchedule(scheduleList)
				.stream().map(day -> new ScheduleDayJson(day))
				.collect(Collectors.toList());

		return new ResponseEntity<List<ScheduleDayJson>>(jSchedule, HttpStatus.CREATED);
	}

}

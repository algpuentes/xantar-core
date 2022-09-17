package com.xantar.xantarcore.schedule;

import java.util.*;
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

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

	public static final String MESSAGE_START_GENERATION_OF_SCHEDULE = "Request [{}] - Start generation of schedule.";
	Logger	LOGGER = LoggerFactory.getLogger(ScheduleController.class);

	private final ScheduleService	scheduleService;

	@Autowired
	ScheduleController(ScheduleService scheduleService) {
		this.scheduleService = Objects.requireNonNull(scheduleService, "Schedule service must not be null");
	}

	@PostMapping()
	public ResponseEntity<GetScheduleResponseJson> generateSchedule(@RequestBody List<ScheduleDayJson> jScheduleRequest) {
		var requestId = UUID.randomUUID();
		LOGGER.info(MESSAGE_START_GENERATION_OF_SCHEDULE, requestId);

		return Optional.ofNullable(jScheduleRequest)
				.filter(list -> !list.isEmpty())
				.map(list -> list.stream()
						.map(ScheduleDayJson::toScheduleDay)
						.collect(Collectors.toList()))
				.map(list -> this.scheduleService.generateSchedule(list).stream()
						.map(ScheduleDayJson::new)
						.collect(Collectors.toList()))
				.map(jSchedule -> new ResponseEntity<>(new GetScheduleResponseJson(jSchedule), HttpStatus.CREATED))
				.orElse(new ResponseEntity<>(new GetScheduleResponseJson(new ArrayList<>()), HttpStatus.CREATED));
	}

}

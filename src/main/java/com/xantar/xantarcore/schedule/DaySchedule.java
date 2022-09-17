package com.xantar.xantarcore.schedule;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public record DaySchedule(Integer id, Long timestamp, List<SlotSchedule> configurations) {

	public DaySchedule(Integer id, Long timestamp, List<SlotSchedule> configurations) {
		this.id = id;
		this.timestamp = timestamp;
		this.configurations = Optional.ofNullable(configurations).map(ArrayList::new).orElse(new ArrayList<>());
	}

	@Override
	public List<SlotSchedule> configurations() {
		return Optional.ofNullable(configurations).map(ArrayList::new).orElse(new ArrayList<>());
	}
}

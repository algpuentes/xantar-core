package com.xantar.xantarcore.schedule;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ScheduleJson {

	public final Integer id;
	public final List<ScheduleDayJson> days;

	public ScheduleJson(List<ScheduleDayJson> days) {
		this.id = null;
		this.days = new ArrayList<ScheduleDayJson>(days);
	}


}

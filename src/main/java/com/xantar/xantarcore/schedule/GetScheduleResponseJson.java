package com.xantar.xantarcore.schedule;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_NULL)
record GetScheduleResponseJson(List<ScheduleDayJson> days) {

    private static final String PROPERTY_DAYS = "days";

    GetScheduleResponseJson(@JsonProperty(PROPERTY_DAYS) List<ScheduleDayJson> days) {
        this.days = Optional.ofNullable(days).map(ArrayList::new).orElse(new ArrayList<>());
    }

    @JsonProperty(PROPERTY_DAYS)
    public List<ScheduleDayJson> retrieveDays() {
        return new ArrayList<>(days);
    }
}

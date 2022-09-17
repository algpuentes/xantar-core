package com.xantar.xantarcore.schedule;

import com.xantar.xantarcore.meals.Meal;
import com.xantar.xantarcore.meals.MealResponseJson;
import com.xantar.xantarcore.meals.Slot;
import com.xantar.xantarcore.meals.SlotResponseJson;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ScheduleControllerTest {

    @Test
    void generateSchedule_returnEmptyResultList_whenRequestedListIsNull() {
        var scheduleController = new ScheduleController(mock(ScheduleService.class));

        var result = scheduleController.generateSchedule(null);
        assertTrue(result.getBody().retrieveDays().isEmpty());
    }


    @Test
    void generateSchedule_returnEmptyResultList_whenRequestedListIsEmpty() {
        var scheduleController = new ScheduleController(mock(ScheduleService.class));

        var result = scheduleController.generateSchedule(new ArrayList<>());
        assertTrue(result.getBody().retrieveDays().isEmpty());
    }

    @Test
    void generateSchedule_returnResultList_whenRequestedListHasValues() {
        var mock = mock(ScheduleService.class);
        var scheduleController = new ScheduleController(mock);

        var requestedList = new ArrayList<ScheduleDayJson>();
        var jSlotSchedule = new ScheduleConfigurationJson(new SlotResponseJson(1, "name"), null);
        var jDaySchedule = new ScheduleDayJson(1, 1234L, Collections.singletonList(jSlotSchedule));
        requestedList.add(jDaySchedule);

        var responseList = new ArrayList<ScheduleDayJson>();
        var jResponseSlotSchedule = new ScheduleConfigurationJson(new SlotResponseJson(1, "name"),
                new MealResponseJson(1, "name", "desc", Collections.singletonList(new SlotResponseJson(1, "name")), null));
        var jResponseDaySchedule = new ScheduleDayJson(1, 1234L, Collections.singletonList(jResponseSlotSchedule));
        responseList.add(jResponseDaySchedule);

        var requestedListMapped = new ArrayList<DaySchedule>();
        var slotSchedule = new SlotSchedule(new Slot(1, "name"), null);
        var daySchedule = new DaySchedule(1, 1234L, Collections.singletonList(slotSchedule));
        requestedListMapped.add(daySchedule);

        var responseListMapped = new ArrayList<DaySchedule>();
        var responseSlotSchedule = new SlotSchedule(new Slot(1, "name"),
                Meal.builder()
                        .withId(1)
                        .withName("name")
                        .withDescription("desc")
                        .withSlots(Collections.singletonList(new Slot(1, "name")))
                        .build());

        var responseDaySchedule = new DaySchedule(1, 1234L, Collections.singletonList(responseSlotSchedule));
        responseListMapped.add(responseDaySchedule);

        when(mock.generateSchedule(requestedListMapped)).thenReturn(responseListMapped);

        var actualResult = scheduleController.generateSchedule(requestedList);

        assertEquals(responseList, actualResult.getBody().retrieveDays());
    }
}
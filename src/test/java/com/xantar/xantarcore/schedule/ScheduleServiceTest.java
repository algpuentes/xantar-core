package com.xantar.xantarcore.schedule;

import com.xantar.xantarcore.common.exceptions.ScheduleProcessingException;
import com.xantar.xantarcore.meals.Meal;
import com.xantar.xantarcore.db.MealsDbService;
import com.xantar.xantarcore.meals.Slot;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ScheduleServiceTest {

    @Test
    void generateSchedule_returnsUniqueMealForSlot_WhenMatchingMealsNumberIsAtLeastRequestedSlotsNumber() {
        var mockMealsService = mock(MealsDbService.class);
        var scheduleService = new ScheduleService(mockMealsService);

        var mealList1 = List.of(
                Meal.builder().withId(1).withName("name").build(),
                Meal.builder().withId(2).withName("name").build(),
                Meal.builder().withId(3).withName("name").build()
        );

        var requestedScheduledDays = List.of(
                new DaySchedule(1, 1234L, List.of(new SlotSchedule(new Slot(1,"slot"), null))),
                new DaySchedule(2, 1234L, List.of(new SlotSchedule(new Slot(1,"slot"), null))),
                new DaySchedule(3, 1234L, List.of(new SlotSchedule(new Slot(1,"slot"), null)))
        );

        when(mockMealsService.findMealsBySlotAndMaxRows(1,3)).thenReturn(mealList1);

        var resultingScheduledDays = scheduleService.generateSchedule(requestedScheduledDays);

        assertEquals(3, resultingScheduledDays.size());
        assertEquals(1, resultingScheduledDays.get(0).configurations().size());
        assertEquals(1, resultingScheduledDays.get(1).configurations().size());
        assertEquals(1, resultingScheduledDays.get(2).configurations().size());
        assertTrue(List.of(1,2,3).containsAll(
                List.of(resultingScheduledDays.get(0).configurations().get(0).meal().id(),
                        resultingScheduledDays.get(1).configurations().get(0).meal().id(),
                        resultingScheduledDays.get(2).configurations().get(0).meal().id())));
    }

    @Test
    void generateSchedule_returnsRepeatedMealsForSlot_WhenMatchingMealsNumberIsLessThanRequestedSlotsNumber() {
        var mockMealsService = mock(MealsDbService.class);
        var scheduleService = new ScheduleService(mockMealsService);

        var mealList1 = List.of(
                Meal.builder().withId(1).withName("name").build()
        );

        var requestedScheduledDays = List.of(
                new DaySchedule(1, 1234L, List.of(new SlotSchedule(new Slot(1,"slot"), null))),
                new DaySchedule(2, 1234L, List.of(new SlotSchedule(new Slot(1,"slot"), null))),
                new DaySchedule(3, 1234L, List.of(new SlotSchedule(new Slot(1,"slot"), null)))
        );

        when(mockMealsService.findMealsBySlotAndMaxRows(1,3)).thenReturn(mealList1);

        var resultingScheduledDays = scheduleService.generateSchedule(requestedScheduledDays);

        assertEquals(3, resultingScheduledDays.size());
        assertEquals(1, resultingScheduledDays.get(0).configurations().size());
        assertEquals(1, resultingScheduledDays.get(1).configurations().size());
        assertEquals(1, resultingScheduledDays.get(2).configurations().size());
        assertEquals(1, resultingScheduledDays.get(0).configurations().get(0).meal().id());
        assertEquals(1, resultingScheduledDays.get(1).configurations().get(0).meal().id());
        assertEquals(1, resultingScheduledDays.get(2).configurations().get(0).meal().id());
    }


    @Test
    void generateSchedule_throwsError_WhenNoMatchingMealsIsFound() {
        var mockMealsService = mock(MealsDbService.class);
        var scheduleService = new ScheduleService(mockMealsService);

        var requestedScheduledDays = List.of(
                new DaySchedule(1, 1234L, List.of(new SlotSchedule(new Slot(1,"slot"), null))),
                new DaySchedule(2, 1234L, List.of(new SlotSchedule(new Slot(1,"slot"), null))),
                new DaySchedule(3, 1234L, List.of(new SlotSchedule(new Slot(1,"slot"), null)))
        );

        when(mockMealsService.findMealsBySlotAndMaxRows(1,3)).thenReturn(Collections.emptyList());

        assertThrows(ScheduleProcessingException.class, () -> scheduleService.generateSchedule(requestedScheduledDays));

    }
}
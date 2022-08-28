package com.xantar.xantarcore.schedule;

import com.xantar.xantarcore.meals.Meal;
import com.xantar.xantarcore.meals.Slot;

public record SlotSchedule(Slot slot, Meal meal) {}

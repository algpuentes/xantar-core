package com.xantar.xantarcore.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IMealsRepository extends JpaRepository<EMeal, Integer> {

	@Query(value="select * from xantar.meals ml left join xantar.slots sl on sl.meal_id=ml.id WHERE sl.slot_id=?1 ORDER BY RANDOM() LIMIT ?2", nativeQuery = true)
	public List<EMeal> findMealsBySlotAndMaxRows(Integer slotId, int maxRows);

}

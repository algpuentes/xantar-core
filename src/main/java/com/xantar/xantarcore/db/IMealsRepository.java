package com.xantar.xantarcore.db;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMealsRepository extends JpaRepository<EMeal, UUID> {

	@Override
	List<EMeal> findAll();
}

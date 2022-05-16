package com.xantar.xantarcore.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
interface ISlotsRepository extends JpaRepository<ESlot, Integer> {

}

package com.bbh.repository;

import com.bbh.domain.DayOffType;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the DayOffType entity.
 */
@SuppressWarnings("unused")
public interface DayOffTypeRepository extends JpaRepository<DayOffType,Long> {

}

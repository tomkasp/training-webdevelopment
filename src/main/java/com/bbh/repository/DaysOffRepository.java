package com.bbh.repository;

import com.bbh.domain.DaysOff;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the DaysOff entity.
 */
@SuppressWarnings("unused")
public interface DaysOffRepository extends JpaRepository<DaysOff,Long> {

}

package com.cms.world.repository;

import com.cms.world.domain.dto.TimeLogDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TimeLogRepository extends JpaRepository<TimeLogDto, Long> {
}

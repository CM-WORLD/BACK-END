package com.cms.world.stepper.repository;

import com.cms.world.stepper.domain.StepperDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface StepperRepository extends JpaRepository<StepperDto, Long> {
}

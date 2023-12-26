package com.cms.world.repository;

import com.cms.world.domain.dto.CommissionDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommissionRepository extends JpaRepository<CommissionDto, Long> {

    List<CommissionDto> findByDelYnContaining(String delYn);
}

package com.cms.world.cms;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommissionRepository extends JpaRepository<CommissionDto, String> {

    List<CommissionDto> findByDelYnContaining(String delYn);

}

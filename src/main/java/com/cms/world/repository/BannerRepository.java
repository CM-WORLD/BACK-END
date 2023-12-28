package com.cms.world.repository;

import com.cms.world.domain.dto.BannerDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BannerRepository extends JpaRepository<BannerDto, Long> {
}

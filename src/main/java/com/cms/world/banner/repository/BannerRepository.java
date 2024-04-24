package com.cms.world.banner.repository;

import com.cms.world.banner.domain.BannerDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BannerRepository extends JpaRepository<BannerDto, Long> {
}

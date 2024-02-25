package com.cms.world.board.repository;

import com.cms.world.board.domain.BbsImgDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BbsImgRepository extends JpaRepository<BbsImgDto, String> {
}

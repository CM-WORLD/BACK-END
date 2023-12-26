package com.cms.world.repository;

import com.cms.world.domain.dto.PostDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<PostDto, Long> {

    List<PostDto> findByTypeContaining(String type);

}

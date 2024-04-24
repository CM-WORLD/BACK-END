package com.cms.world.post;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<PostDto, Long> {

    List<PostDto> findByTypeContaining(String type);

    List<PostDto> findAll();

}

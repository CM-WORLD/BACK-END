package com.cms.world.service;


import com.cms.world.domain.dto.ReviewDto;
import com.cms.world.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository repository;

    public List<ReviewDto> list () {
        return repository.findAll(Sort.by("regDate"));
    }
}

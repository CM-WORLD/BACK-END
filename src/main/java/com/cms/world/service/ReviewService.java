package com.cms.world.service;


import com.cms.world.apply.domain.ApplyDto;
import com.cms.world.domain.dto.ReviewDto;
import com.cms.world.domain.vo.PageVo;
import com.cms.world.domain.vo.ReviewVo;
import com.cms.world.apply.repository.CmsApplyRepository;
import com.cms.world.repository.ReviewRepository;
import com.cms.world.authentication.domain.AuthTokensGenerator;
import com.cms.world.utils.GlobalCode;
import com.cms.world.utils.GlobalStatus;
import com.cms.world.utils.StringUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository repository;

    private final CmsApplyRepository cmsApplyRepository;

    private final AuthTokensGenerator authTokensGenerator;

    public Page<ReviewDto> list (HttpServletRequest request, PageVo vo, String isMemberYn) {
        Pageable pageable = PageRequest.of(vo.getPage(), vo.getSize(), Sort.by(Sort.Direction.DESC, "regDate"));

        if (isMemberYn.equals("N")) return repository.findAll(pageable);
        return repository.findLitByMemberId(authTokensGenerator.extractMemberIdFromReq(request),  pageable);

    }

    @Transactional
    public ReviewDto create (ReviewVo vo, HttpServletRequest request) throws Exception {
        Long memberId = authTokensGenerator.extractMemberIdFromReq(request); // req로부터 id 추출

        ReviewDto dto = new ReviewDto();
        ApplyDto applyDto = cmsApplyRepository.findById(vo.getCmsApplyId())
                .orElseThrow(() -> new Exception("ReviewService.create() : 신청서가 존재하지 않습니다."));

        dto.setApplyDto(applyDto);
        dto.setMemberId(memberId);
        dto.setContent(vo.getContent());
        dto.setNickName(StringUtil.isEmpty(vo.getNickName()) ? "익명" : vo.getNickName());
        dto.setCmsId(dto.getApplyDto().getCmsDto().getId());

        ReviewDto newReview = repository.save(dto);
        if(newReview != null) {
            applyDto.setStatus(GlobalCode.CMS_REVIEW.getCode()); // 리뷰 작성 성공시 상태 변경
        }
        return newReview;
    }

    @Transactional
    public int toggle (Long id) throws Exception {
        ReviewDto dto = repository.findById(id)
                .orElseThrow(() -> new Exception("ReviewService.toggle() : 리뷰가 존재하지 않습니다."));
        dto.setDisplayYn(dto.getDisplayYn().equals("Y") ? "N" : "Y");
        return GlobalStatus.SUCCESS.getStatus();
    }

}

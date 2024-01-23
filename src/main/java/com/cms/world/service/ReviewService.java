package com.cms.world.service;


import com.cms.world.auth.MemberRepository;
import com.cms.world.domain.dto.ReviewDto;
import com.cms.world.domain.vo.PageVo;
import com.cms.world.domain.vo.ReviewVo;
import com.cms.world.repository.CmsApplyRepository;
import com.cms.world.repository.ReviewRepository;
import com.cms.world.security.jwt.JwtTokensGenerator;
import com.cms.world.utils.StringUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository repository;

    private final CmsApplyRepository cmsApplyRepository;

    private final MemberRepository memberRepository;

    private final JwtTokensGenerator jwtTokensGenerator;

    public Page<ReviewDto> list (PageVo vo, HttpServletRequest request) {
        Pageable pageable = PageRequest.of(vo.getPage(), vo.getSize(), Sort.by(Sort.Direction.DESC, "regDate"));
        Long memberId = jwtTokensGenerator.extractMemberIdFromReq(request); // req로부터 id 추출

        if (!memberRepository.findById(memberId).isPresent()) return repository.findAll(pageable);
        return repository.findByMemberId(memberId,pageable);
    }

    public Long create (ReviewVo vo, HttpServletRequest request) throws Exception {
        Long memberId = jwtTokensGenerator.extractMemberIdFromReq(request); // req로부터 id 추출

        ReviewDto dto = new ReviewDto();
        dto.setApplyDto(cmsApplyRepository.findById(vo.getCmsApplyId())
                .orElseThrow(() -> new Exception("ReviewService.create() : 신청서가 존재하지 않습니다.")));
        dto.setMemberId(memberId);
        dto.setContent(vo.getContent());
        dto.setNickName(StringUtil.isEmpty(vo.getNickName()) ? "익명" : vo.getNickName());
        dto.setCmsId(dto.getApplyDto().getCmsDto().getId());

        return repository.save(dto).getId();
    }
}

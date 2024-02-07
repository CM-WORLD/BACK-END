package com.cms.world.apply.service;


import com.cms.world.apply.domain.CmsApplyDto;
import com.cms.world.apply.domain.CmsApplyImgDto;
import com.cms.world.authentication.member.domain.MemberDto;
import com.cms.world.authentication.member.domain.MemberRepository;
import com.cms.world.common.DtoMapper;
import com.cms.world.domain.dto.*;
import com.cms.world.apply.domain.CmsApplyVo;
import com.cms.world.apply.repository.CmsApplyImgRepository;
import com.cms.world.apply.repository.CmsApplyRepository;
import com.cms.world.repository.CmsPayRepository;
import com.cms.world.repository.CommissionRepository;
import com.cms.world.service.S3UploadService;
import com.cms.world.service.TimeLogService;
import com.cms.world.utils.DateUtil;
import com.cms.world.utils.GlobalStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CmsApplyService {

    private final CmsApplyRepository repository;
    private final CmsApplyImgRepository imgRepository;

    private final S3UploadService uploadService;

    private final TimeLogService timeLogService;

    private final CmsPayRepository payRepository;

    private final CommissionRepository commissionRepository;

    private final MemberRepository memberRepository;

    /* 커미션 신청 */
    @Transactional
    public String insert(CmsApplyVo vo) throws Exception{
        CmsApplyDto dto = DtoMapper.map(vo, CmsApplyDto.class); // vo와 dto 매핑

        Optional<CommissionDto> cmsDto = commissionRepository.findById(vo.getCmsId());
            if (cmsDto.isPresent()) dto.setCmsDto(cmsDto.get());
            else throw new Exception("apply.insert :: cmsDto not found");

            Optional<MemberDto> memberDto = memberRepository.findById(vo.getUserId());
            if (memberDto.isPresent()) dto.setMemberDto(memberDto.get());
            else throw new Exception("apply.insert :: memberDto not found");

            CmsApplyDto newDto = repository.save(dto);
            // newDto가 == null일 경우 예외를 던진다.

            if (vo.getImgList() != null && !vo.getImgList().isEmpty()) {
                for (MultipartFile img : vo.getImgList()) {
                    String awsUrl = uploadService.saveFile(img, "apply");
                    CmsApplyImgDto imgDto = new CmsApplyImgDto();
                    imgDto.setImgUrl(awsUrl);
                    imgDto.setApplyDto(newDto);
                    imgRepository.save(imgDto);
                    // save한 값이 == null일 경우 예외를 던진다.
                }
            }
            return newDto.getId();
    }

    /* 커미션 전체 리스트 조회 (등록 최신순) */
    public List<CmsApplyDto> list () {
        List<CmsApplyDto> list = repository.findAll(Sort.by("regDate"));
        return list;
    }

    /* 사용자별 신청 리스트 조회 */
    public Page<CmsApplyDto> listByMemberID (Long id, int page, int size) {
        Pageable pageable = PageRequest.of(page, size,  Sort.by(Sort.Direction.DESC, "regDate"));
//        return repository.findByMemberDto_Id(id, pageable);
        return repository.findListByMemberId(id, pageable);
    }

    /* 커미션 신청 상세 */
    public CmsApplyDto detail (String id) throws Exception{
        Optional<CmsApplyDto> applyDto = repository.findById(id);
        if(applyDto.isPresent()) {
            // 신청아이디가 속한 커미션 정보 찾기
            Optional<CommissionDto> cmsDto = commissionRepository.findById(applyDto.get().getCmsDto().getId());
            applyDto.get().setCmsName(cmsDto.get().getName());
            return applyDto.get();
        } else {
            throw new Exception("applyDto not found");
        }
    }
    
    /* 커미션 신청 이미지 리스트 상태별 조회  (신청/완료) */
    public List<CmsApplyImgDto> imgListByStatus (String id, String status) {
        Optional<CmsApplyDto> applyDto = repository.findById(id);
        List<CmsApplyImgDto> imgList = new ArrayList<>();
//        if (applyDto.isPresent()) {
//            imgList = applyDto.get().getCmsApplyImgDto().stream()
//                   .filter(img -> img.getStatus().equals(status))
//                   .toList();
//        }
        return imgList;
    }

    /* 해당 커미션에 속한 신청중/예약 신청 건 cnt 조회 */
    public Long applyCntByStatus (String status, String cmsId) throws Exception{
        Optional<CommissionDto> cmsDto = commissionRepository.findById(cmsId);
        if (cmsDto.isPresent()) {
           return Long.valueOf(repository.countByStatusAndCmsDto_Id(status, cmsDto.get().getId()));
        } else throw new Exception("applyCntByStatus error: cmsDto not found");
    }

    /* 신청서당 영수증 1:1 조회 */
    public CmsPayDto paymentDetail (String cmsApplyId) throws Exception {
        Optional<CmsApplyDto> applyDto = repository.findById(cmsApplyId);
        if(!applyDto.isPresent()) throw new Exception ("can not find applyDto");

        return payRepository.findByApplyDto(applyDto.get());
    }

}

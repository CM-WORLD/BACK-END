package com.cms.world.service;


import com.cms.world.auth.MemberRepository;
import com.cms.world.domain.dto.*;
import com.cms.world.domain.vo.CmsApplyVo;
import com.cms.world.repository.CmsApplyImgRepository;
import com.cms.world.repository.CmsApplyRepository;
import com.cms.world.repository.CmsPayRepository;
import com.cms.world.repository.CommissionRepository;
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

    private MemberRepository memberRepository;

    /* 커미션 신청 */
    @Transactional
    public String insert(CmsApplyVo vo) throws Exception{
            CmsApplyDto dto = new CmsApplyDto();
            dto.setStatus(vo.getStatus());
            dto.setTitle(vo.getTitle());
            dto.setContent(vo.getContent());
            dto.setBankOwner(vo.getBankOwner());

            Optional<CommissionDto> cmsDto = commissionRepository.findById(vo.getCmsId());
            if (cmsDto.isPresent()) dto.setCmsDto(cmsDto.get());
            else throw new Exception("apply.insert :: cmsDto not found");

            Optional<MemberDto> memberDto = memberRepository.findById(vo.getUserId());
            if (memberDto.isPresent()) dto.setMemberDto(memberDto.get());
            else throw new Exception("apply.insert :: memberDto not found");

            CmsApplyDto newDto = repository.save(dto);

            if (vo.getImgList() != null && !vo.getImgList().isEmpty()) {
                for (MultipartFile img : vo.getImgList()) {
                    String awsUrl = uploadService.saveFile(img, "apply");
                    CmsApplyImgDto imgDto = new CmsApplyImgDto();
                    imgDto.setImgUrl(awsUrl);
                    imgDto.setApplyDto(newDto);
                    imgRepository.save(imgDto);
                }
            }
            return newDto.getId();
    }

    /* 커미션 전체 리스트 조회 (등록 최신순) */
    public List<CmsApplyDto> list () {
        List<CmsApplyDto> list = repository.findAll(Sort.by("regDate"));
        return list;
    }

    // OK
    @Transactional
    public int updateStatus (String id, String status) {
        try {
            CmsApplyDto dto = repository.findById(id).get();
            dto.setStatus(status);
            return GlobalStatus.EXECUTE_SUCCESS.getStatus();
        } catch (Exception e) {
            return GlobalStatus.EXECUTE_FAILED.getStatus();
        }
    }

    /* 커미션 신청 타입 변경 (1인/단체) */
    @Transactional
    public int updateTp (String id, String cmsType) throws Exception{
        Optional<CmsApplyDto> dto = repository.findById(id);
        if(!dto.isPresent()) throw new Exception("updateTp error: applyDto not found");
        dto.get().setCmsType(cmsType);

        return GlobalStatus.EXECUTE_SUCCESS.getStatus();
    }

    /* 조건당 커미션 신청 수 조회 */
    public long cntByStatus (String status) {
        return repository.countByStatus(status);
    }

    /* 사용자별 신청 리스트 조회 */
    public Page<CmsApplyDto> listByMemberID (Long id, int page, int size) {
        Pageable pageable = PageRequest.of(page, size,  Sort.by(Sort.Direction.DESC, "regDate"));
        return repository.findByMemberDto_Id(id, pageable);
    }

    /* 커미션 신청 상세 */
    public CmsApplyDto detail (String id) throws Exception{
        Optional<CmsApplyDto> applyDto = repository.findById(id);
        if(applyDto.isPresent()) {
            return applyDto.get();
        } else {
            throw new Exception("applyDto not found");
        }
    }
    
    /* 커미션 신청 이미지 리스트 조회 */
    public List<CmsApplyImgDto> imgListById (String id) {
        CmsApplyDto applyDto = repository.findById(id).get();
        return imgRepository.findAllByApplyDto(applyDto);
    }

    /* 신청서당 영수증 1:1 조회 */
    public CmsPayDto paymentDetail (String cmsApplyId) throws Exception {
        Optional<CmsApplyDto> applyDto = repository.findById(cmsApplyId);
        if(!applyDto.isPresent()) throw new Exception ("can not find applyDto");

        return payRepository.findByApplyDto(applyDto.get());
    }

}

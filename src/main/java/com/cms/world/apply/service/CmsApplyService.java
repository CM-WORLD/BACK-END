package com.cms.world.apply.service;


import com.cms.world.alert.AlertMsg;
import com.cms.world.alert.telegram.TelegramBotApi;
import com.cms.world.apply.domain.CmsApplyDto;
import com.cms.world.apply.domain.CmsApplyImgDto;
import com.cms.world.authentication.member.domain.MemberDto;
import com.cms.world.authentication.member.domain.MemberRepository;
import com.cms.world.common.DtoMapper;
import com.cms.world.domain.dto.*;
import com.cms.world.apply.domain.CmsApplyVo;
import com.cms.world.apply.repository.CmsApplyImgRepository;
import com.cms.world.apply.repository.CmsApplyRepository;
import com.cms.world.payment.domain.InvoiceDto;
import com.cms.world.payment.domain.PaymentDto;
import com.cms.world.payment.repository.CmsPayRepository;
import com.cms.world.payment.repository.InvoiceRepository;
import com.cms.world.repository.CommissionRepository;
import com.cms.world.service.S3UploadService;
import com.cms.world.stepper.domain.StepperDto;
import com.cms.world.stepper.service.StepperService;
import com.cms.world.utils.GlobalCode;
import com.cms.world.utils.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CmsApplyService {

    private final CmsApplyRepository repository;

    private final CmsApplyImgRepository imgRepository;

    private final S3UploadService uploadService;

    private final StepperService stepperService;

    private final CmsPayRepository payRepository;

    private final InvoiceRepository invoiceRepository;

    private final CommissionRepository commissionRepository;

    private final MemberRepository memberRepository;

    private final TelegramBotApi TelegramBotApi;


    /* 전화번호 유효성 검사 */
    private boolean notValidPhoneNumber(String phoneNumber) {
        if (StringUtil.isEmpty(phoneNumber)) return true;

        //TODO:: 전화번호 정규식 체크
        String regex = "^\\d{2,3}\\d{3,4}\\d{4}$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);

        return !matcher.matches();
    }

    /* 커미션 신청 */
    @Transactional
    public String insert(CmsApplyVo vo) throws Exception {
        CmsApplyDto dto = DtoMapper.map(vo, CmsApplyDto.class); // vo와 dto 매핑

        Optional<CommissionDto> cmsDto = commissionRepository.findById(vo.getCmsId());
        if (cmsDto.isPresent()) dto.setCmsDto(cmsDto.get());
        else throw new Exception("존재하지 않는 신청서 아이디입니다.");

        Optional<MemberDto> memberDto = memberRepository.findById(vo.getUserId());
        if (memberDto.isPresent()) dto.setMemberDto(memberDto.get());
        else throw new Exception("존재하지 않는 회원 정보입니다.");

        /* 알림 수신 여부 시 전화번호 체크 */
        if (vo.getSendAlertYn().equals("Y") && notValidPhoneNumber(vo.getPhoneNumber())) {
            throw new Exception("유효하지 않은 전화번호입니다.");
        }

        /* 예약 또는 신청으로 신청서 상태 지정 */
        dto.setStatus(vo.getStatus());

        // 신청서 저장
        CmsApplyDto newApplication = repository.save(dto);
        if (newApplication == null) throw new Exception("신청서 저장 중 오류가 발생했습니다.");

        // 신청서 id로 신청서 이미지들 저장
        for (MultipartFile img : vo.getImgList()) {
            try {
                String awsUrl = uploadService.saveFile(img, "apply");
                CmsApplyImgDto imgDto = new CmsApplyImgDto();
                imgDto.setImgUrl(awsUrl);
                imgDto.setApplyDto(newApplication);
                imgRepository.save(imgDto);
            } catch (Exception e) {
                throw new Exception("신청서 이미지 저장 중 오류가 발생했습니다. 파일명의 길이, 확장자를 재확인 해주세요");
            }
        }

        // 신청서 id로 타임로그 현재 시간, 상태로 저장
        stepperService.insertStep(newApplication.getId(), newApplication.getStatus());

        // 텔레그램으로 jinvickybot에 신청 알림 전송 // 테스트 ok
        AlertMsg alertMsg = AlertMsg.builder()
                .cmsApplyStatus(newApplication.getStatus())
                .cmsName(cmsDto.get().getName())
                .phoneNum(vo.getPhoneNumber())
                .receiverNickName(memberDto.get().getNickName()) // 신청자를 수신자로 설정
                .build();
        String messageStr = alertMsg.createAlertMsg();
        TelegramBotApi.sendAlertToAdmin(messageStr);

        return newApplication.getId();
    }


    /* 사용자별 신청 리스트 조회 */
    public Page<CmsApplyDto> listByMemberID (Long id, int page, int size) {
        Pageable pageable = PageRequest.of(page, size,  Sort.by(Sort.Direction.DESC, "regDate"));
        return repository.findListByMemberId(id, pageable);
    }

    /* 커미션 신청 상세 조회 */
    public Map<String, Object> detail (String id) throws Exception {
        Optional<CmsApplyDto> optApply = repository.findById(id);
        if (!optApply.isPresent()) throw new Exception("존재하지 않는 신청서 ID입니다.");

        Map<String, Object> map = new HashMap<>();
        CmsApplyDto applyDto = optApply.get();

        // 신청서 소속 커미션 이름 저장
        applyDto.setCmsName(applyDto.getCmsDto().getName());
        map.put("applyDto", applyDto);

        // 신청 이미지 리스트 조회
        List<CmsApplyImgDto> appliedImageList = imgRepository.findByStatus(GlobalCode.APPLIED_IMG.getCode());
        map.put("appliedImageList", appliedImageList);
        //----- 여기까지 신청서 정보 ------

        // 신청서별 완료 이미지 리스트 조회
        List<CmsApplyImgDto> completeImageList = imgRepository.findByStatus(GlobalCode.COMPLETE_IMG.getCode());
        map.put("completeImageList", completeImageList);

        // 신청서별 인보이스 리스트 조회
        List<InvoiceDto> invoiceList = invoiceRepository.findByApplyDto_Id(applyDto.getId());
        map.put("invoiceList", invoiceList);
//
//        // 인보이스별 결제정보 조회
//        List<PaymentDto> paymentList = payRepository.findByInvoiceDto_Id()

        // 신청서별 타임로그 리스트 조회
        List<StepperDto> stepperList = stepperService.getListByCmsApplyId(applyDto.getId());
        map.put("stepperList", stepperList);

        return map;
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
    public InvoiceDto paymentDetail (String cmsApplyId) throws Exception {
        Optional<CmsApplyDto> applyDto = repository.findById(cmsApplyId);
        if(!applyDto.isPresent()) throw new Exception ("can not find applyDto");

        return payRepository.findByApplyDto(applyDto.get());
    }

}

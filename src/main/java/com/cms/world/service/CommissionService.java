package com.cms.world.service;


import com.cms.world.domain.dto.CommissionDto;
import com.cms.world.domain.vo.CommissionVo;
import com.cms.world.repository.CommissionRepository;
import com.cms.world.utils.GlobalCode;
import com.cms.world.utils.GlobalStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CommissionService {

    private final CommissionRepository repository;

    public CommissionService(CommissionRepository repository) {
        this.repository = repository;
    }

    @Autowired
    private S3UploadService s3UploadService;

    @Transactional
    public int insert(CommissionVo vo) throws Exception {
        String imgUrl = s3UploadService.saveFile(vo.getProfileImg(), "cmsList");

        CommissionDto dto = new CommissionDto();
        dto.setName(vo.getName());
        dto.setContent(vo.getContent());
        dto.setProfileImg(imgUrl);

        repository.save(dto);
        return GlobalStatus.EXECUTE_SUCCESS.getStatus();
    }

    public List<CommissionDto> list(String delYn) {
        return repository.selectList();
//        return repository.findByDelYnContaining(delYn);
    }

    /* 커미션 전체 열기/닫기 */
    @Transactional
    public int toggleAllStatus (String status) {
        try {
            List<CommissionDto> list = repository.findAll();
            for(CommissionDto item: list) {
                item.setStatus(status);
            }
            return GlobalStatus.EXECUTE_SUCCESS.getStatus();
        } catch (Exception e) {
            return GlobalStatus.EXECUTE_FAILED.getStatus();
        }
    }

    @Transactional
    public int toggleStatus (String id) {
        try {
            CommissionDto dto = repository.findById(id).get();
            dto.setStatus(dto.getStatus().equals(GlobalCode.CMS_CLOSED.getCode())
                    ? GlobalCode.CMS_OPENED.getCode()
                    : GlobalCode.CMS_CLOSED.getCode()
            );
            return GlobalStatus.EXECUTE_SUCCESS.getStatus();
        } catch (Exception e) {
            return GlobalStatus.EXECUTE_FAILED.getStatus();
        }
    }

    // 커미션 아이디 조회
    public Optional<CommissionDto> getById (String id){
        return repository.findById(id);
    }
}

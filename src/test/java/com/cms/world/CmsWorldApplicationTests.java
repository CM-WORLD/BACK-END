package com.cms.world;

import com.cms.world.domain.dto.CmsApplyDto;
import com.cms.world.domain.dto.CmsApplyImgDto;
import com.cms.world.repository.CmsApplyImgRepository;
import com.cms.world.repository.CmsApplyRepository;
import com.cms.world.utils.GlobalCode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@SpringBootTest
class CmsWorldApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private CmsApplyRepository repository;

    @Autowired
    private CmsApplyImgRepository imgRepository;

    @Test
    void cmsInsert () {
        CmsApplyDto input = CmsApplyDto.builder().content("메이플 2명 배경 신청합니다.")
                .cmsType(GlobalCode.TYPE_COUP_BG.getCode())
                .userName("류시아")
                .bankOwner("유시아")
                .build();

        CmsApplyDto dto = repository.save(input);

        CmsApplyImgDto inputImgs = CmsApplyImgDto.builder()
                .imgUrl("https://jvk-world.s3.ap-northeast-2.amazonaws.com/test_01.jpg")
                .applyDto(dto)
                .build();

        imgRepository.save(inputImgs);
    }

//    @Transactional //이걸로 가둬두어야 변경감지가 가능하다.
//    @Transactional
    //이걸 추가했더니 update가 동작을 안한다. ?? 왜 ??
    @Test
    public void updateStatus () { //커미션 신청 상태 변경
        String cms_uuid = "f0a532ba-81ae-4cc5-add7-531708a4e470";
        CmsApplyDto dto2 = repository.findById(cms_uuid).get();

        dto2.setCmsType(GlobalCode.TYPE_SINGLE.getCode());
        dto2.setContent("수정 요청 드립니다.");
        dto2.setStatus(GlobalCode.CMS_REQ_EDITING.getCode());
        repository.save(dto2);

        System.out.println("dto2 = " + dto2);

    }

    @Test
    public void list () {
        List<CmsApplyDto> list = repository.findAll(Sort.by("regDate"));

        for(CmsApplyDto dto : list) {
            System.out.println("dto....... =  " + dto.getRegDate());
        }

    }
}

package com.cms.world;

import com.cms.world.domain.dto.BoardDto;
import com.cms.world.domain.dto.CmsApplyDto;
import com.cms.world.domain.common.AlertMsg;
import com.cms.world.domain.dto.ReplyDto;
import com.cms.world.domain.social.TelegramChat;
import com.cms.world.repository.BoardRepository;
import com.cms.world.repository.CmsApplyImgRepository;
import com.cms.world.repository.CmsApplyRepository;
import com.cms.world.repository.ReplyRepository;
import com.cms.world.utils.GlobalCode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;


@SpringBootTest
class CmsWorldApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private CmsApplyRepository repository;

    @Autowired
    private CmsApplyImgRepository imgRepository;

//    @Test
//    void cmsInsert () {
//        CmsApplyDto input = CmsApplyDto.builder().content("메이플 2명 배경 신청합니다.")
//                .cmsType(GlobalCode.TYPE_COUP_BG.getCode())
//                .userName("류시아")
//                .bankOwner("유시아")
//                .build();
//
//        CmsApplyDto dto = repository.save(input);
//
//        CmsApplyImgDto inputImgs = CmsApplyImgDto.builder()
//                .imgUrl("https://jvk-world.s3.ap-northeast-2.amazonaws.com/test_01.jpg")
//                .applyDto(dto)
//                .build();
//
//        imgRepository.save(inputImgs);
//    }

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
    
    @Autowired
    AlertMsg alertMsg;

    @Autowired
    TelegramChat telegramChat;
    @Test
    public void createMsg () {
        AlertMsg msg = alertMsg;
        msg.setText("test alert.. aply...test....... ");
        telegramChat.sendAlert(msg);
    }

    @Autowired
    ReplyRepository replyRepository;

    @Autowired
    BoardRepository boardRepository;
    @Test
    public void replyList () {
        BoardDto dto = boardRepository.findById(1L).get();
        List<ReplyDto> list = replyRepository.findReplyDtoByBoardDto(dto);
        System.out.println("dto = " + list);
    }


    @Test
    public void inquiryList () {
        for(int i = 0; i < 15; i++) {
            BoardDto dto = new BoardDto();
            dto.setContent("test");
            dto.setWriter("jinvicky");
            dto.setTitle("title ..... ");
            dto.setType(GlobalCode.BBS_INQUIRY.getCode());

            boardRepository.save(dto);
        }

        int page = 0;
        int size = 6;
        Pageable pageable = PageRequest.of(page, size);
        Page<BoardDto> pageList = boardRepository.findAll(pageable);

        for(BoardDto item : pageList) {
            System.out.println("item = " + item);
        }
    }
}

package com.cms.world;

import com.cms.world.domain.dto.*;
import com.cms.world.domain.common.AlertMsg;
import com.cms.world.domain.social.TelegramChat;
import com.cms.world.repository.*;
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
    private CmsApplyRepository cmsApplyRepository;

    @Autowired
    private CmsApplyImgRepository cmsApplyImgRepository;


    @Autowired
    BoardRepository boardRepository;

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
    MemberRepository memberRepository;

    @Test
    public void insertUser () {
        MemberDto dto = new MemberDto();
        dto.setEmail("jinvicky17@gmail.com");
        dto.setNickName("user_011007");

        memberRepository.save(dto);

    }

    @Test
    public void findBbsByNick () {
        Pageable pageable = PageRequest.of(0, 10,  Sort.by(Sort.Direction.DESC, "regDate"));
        Page<BoardDto> dtoList = boardRepository.findAllByBbsCodeAndNickNameContainingIgnoreCase(GlobalCode.BBS_INQUIRY.getCode(), "user_011007", pageable);
        System.out.println("pageable = " + dtoList);
        
        for(BoardDto item: dtoList) {
            System.out.println("item.getContent() = " + item.getContent());
        }
    }

    @Autowired
    ReplyRepository replyRepository;
    @Test
    public void replyInsert () {
        //일단 게시글을 하나 가져와서
        BoardDto bbsDto = boardRepository.findById(1L).get(); //실존하는 2L
        ReplyDto replyDto = new ReplyDto();
        replyDto.setNickName("user_jinvicky");
        replyDto.setContent("안녕하세요. 관리자 걍진입니다. 해당 문의 주신 사항은.....");
        replyDto.setBoardDto(bbsDto);

        replyRepository.save(replyDto);

    }

    @Test
    public void bbsDetail () {
        BoardDto dto = boardRepository.findBoardDtoByBbsCodeAndId(GlobalCode.BBS_INQUIRY.getCode(), 1L);
        System.out.println("dto.toString() = " + dto.getContent());
    }

    @Test
    public void replyList () {
        //게시물 1L 당 댓글리스트 다 가져오기.
        BoardDto dto = boardRepository.findById(1L).get();
        List<ReplyDto> replyList = replyRepository
                .findByBoardDto(dto);

        System.out.println("replyList = " + replyList.size());
    }

    @Test
    public void applyImgList () {
        String uuid = "1eadfe86-6a89-4d79-bde4-90487f075117";
        CmsApplyDto applyDto = cmsApplyRepository.findById(uuid).get();
        List<CmsApplyImgDto> list = cmsApplyImgRepository.findAllByApplyDto(applyDto);
        
        for(CmsApplyImgDto item : list) {
            System.out.println("item.getImgUrl() = " + item.getImgUrl());
        }
    }
}

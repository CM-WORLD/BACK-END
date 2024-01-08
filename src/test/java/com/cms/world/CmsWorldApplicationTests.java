package com.cms.world;

import com.cms.world.auth.MemberService;
import com.cms.world.auth.jwt.AuthTokensGenerator;
import com.cms.world.auth.jwt.JwtTokenProvider;
import com.cms.world.domain.dto.*;
import com.cms.world.domain.common.AlertMsg;
import com.cms.world.domain.social.TelegramChat;
import com.cms.world.repository.*;
import com.cms.world.utils.GlobalCode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.io.UnsupportedEncodingException;
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
    
//    too short.... 
//    @Test
//    public void jwtSecretKey () {
//        //jwt Secret key create
//        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
//        String secretString = Encoders.BASE64.encode(key.getEncoded());
//        System.out.println("key = " + secretString);
//    }
    
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Test
    public void jwtExpireTest () {
        String accessToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiZXhwIjoxNzA0NDQxNjk1fQ.QrzrNRXdXOKg7e4IL6_KISEi0Ckzk7Bj670kxeW2RMij0_cifynK9ca3JCxTOjtNbW0cCWE_0_O3u52PkA0nFw"; //만료된 토큰
        accessToken ="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiZXhwIjoxNzA0NDQzODAyfQ.TmijjdRZ2Gj-HuzujfLg7fYYcLOZsXqcNDxBPPWATmlzuwv-KWlzqLaHxrHXO1SczUuA0qPOCVe2tZlV7_k4zg"; //유효한 토큰
        System.out.println("authTokensGenerator.validateToken(accessToken) = " + jwtTokenProvider.validateToken(accessToken));
    }
    
    @Autowired
    AuthTokensGenerator tokensGenerator;
    
    @Test
    public void claimsTest () {
        String accessToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiZXhwIjoxNzA0NjI0ODU2fQ.oS5o16xs0Z6Y5YiOBy1eq7xGSXWw0SK3W-7rvEFHVB-brSgYqLU5I8iQdtOFp50s4FZXanaSWWlzAu5PNMhOmw";
        Object obj = jwtTokenProvider.extractSubject(accessToken);
        System.out.println("obj.toString() = " + obj.toString());

    }
}

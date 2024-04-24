package com.cms.world;

import com.cms.world.alert.telegram.ChatBotApi;
import com.cms.world.apply.domain.ApplyDto;
import com.cms.world.apply.repository.CmsApplyImgRepository;
import com.cms.world.apply.repository.CmsApplyRepository;
import com.cms.world.alert.AlertMsg;
import com.cms.world.alert.AlertService;
import com.cms.world.board.repository.BoardRepository;
import com.cms.world.board.repository.reply.ReplyRepository;
import com.cms.world.payment.domain.PaymentDto;
import com.cms.world.payment.repository.PaymentRepository;
import com.cms.world.common.code.GlobalCode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


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
    AlertService alertService;

    @Autowired
    ChatBotApi chatBotApi;

    @Autowired
    ReplyRepository replyRepository;

    @Autowired
    PaymentRepository paymentRepository;

    @Test
    public void test() {
        PaymentDto paymentDto = new PaymentDto();

        ApplyDto applyDto = cmsApplyRepository.findById("ca692002-5681-4b04-adbe-655a4fd2c039").get();
        paymentDto.setApplyDto(applyDto);
        paymentDto.setStatus(GlobalCode.PAY_PENDING.getCode());
        paymentDto.setPaymentMethod("BANK_TRANSFER");
        paymentDto.setAmount(10000);
        paymentDto.setRegDate("2021-08-01");

        paymentRepository.save(paymentDto);


    }

}

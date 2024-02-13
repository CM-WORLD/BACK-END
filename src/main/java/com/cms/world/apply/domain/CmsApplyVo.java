package com.cms.world.apply.domain;


import com.cms.world.domain.vo.BoardVo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@ToString
public class CmsApplyVo {

    public interface TitleCheck {}
    public interface ContentCheck {}

    public interface bankOwnerCheck {}
    public interface ImgListCheck {}

    private String cmsId; //신청하려는 커미션 아이디
    private Long userId; //신청자 아이디

    @Schema(description = "신청서 상태" , allowableValues = {"신청", "예약"})
    private String status;

    @GroupSequence({TitleCheck.class, ContentCheck.class, bankOwnerCheck.class, ImgListCheck.class})
    public interface CmsApplyVoSequence {}

    @NotEmpty(message = "{empty.title}", groups = TitleCheck.class)
    @Size(min = 5, max = 30, message = "{invalid.title}", groups = TitleCheck.class)
    @Schema(description = "신청서 제목")
    private String title;

    @NotEmpty(message = "{empty.content}", groups = ContentCheck.class)
    @Size(min = 10, max = 1000, message = "{invalid.content}", groups = ContentCheck.class)
    @Schema(description = "신청서 내용")
    private String content;

    @NotEmpty(message = "{empty.bankOwner}", groups = bankOwnerCheck.class)
    @Schema(description = "예금주명", example="홍길동")
    private String bankOwner;

    @Schema(description = "알림 수신 여부", example = "Y", allowableValues = {"Y", "N"}, defaultValue = "N")
    private String sendAlertYn;

    @Schema(description = "휴대전화번호", example = "010-1234-5678")
    private String phoneNumber;

    @NotEmpty(message = "{empty.img}", groups = ImgListCheck.class)
    @Schema(description = "신청 이미지 리스트", required = true)
    private List<MultipartFile> imgList;
}

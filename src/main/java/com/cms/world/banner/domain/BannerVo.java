package com.cms.world.banner.domain;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ToString
@Schema(title = "배너")
public class BannerVo {

    @Schema(description = "배너 이미지 경로")
    private MultipartFile img;

    @Schema(description = "배너 클릭 시 이동 링크", example = "http://localhost:8080/")
    private String hrefUrl;

    @Schema(description = "배너 관련 코멘트", example = "단체 커미션 배너")
    private String comment;

    @Schema(description = "배너 노출 시작일", example = "2022-01-01")
    private String startDate;

    @Schema(description = "배너 노출 종료일", example = "2022-12-31")
    private String endDate;

    @Schema(description = "배너 삭제 여부", example = "N", defaultValue = "N")
    private String delYn;
}

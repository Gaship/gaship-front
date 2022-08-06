package shop.gaship.gashipfront.inquiry.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Getter;

/**
 * 문의 상세조회시 body에 담아야할 정보들을 가지는 객체입니다.
 *
 * @author 최겸준
 * @since 1.0
 */
@Getter
public class InquiryDetailsResponseDto {
    private Integer inquiryNo;
    private Integer productNo;

    private String memberNickname;
    private String employeeName;
    private String processStatus;
    private String productName;

    private String title;
    private String inquiryContent;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime registerDatetime;

    private String answerContent;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime answerRegisterDatetime;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime answerModifyDatetime;

}

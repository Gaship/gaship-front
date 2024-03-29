package shop.gaship.gashipfront.inquiry.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * 문의에 대한 답변을 등록하기위해 사용하는 DTO 클래스입니다.
 *
 * @author 최겸준
 * @since 1.0
 */
@Getter
@Setter
public class InquiryAnswerRequestDto {

    @Min(value = 1, message = "inquiryNo 는 최소값이 1입니다.")
    private Integer inquiryNo;

    @Min(value = 1, message = "employeeNo 는 최소값이 1입니다.")
    private Integer employeeNo;

    @NotBlank(message = "answerContent 는 필수값이며 공백이나 빈문자열이면 안됩니다.")
    private String answerContent;
}

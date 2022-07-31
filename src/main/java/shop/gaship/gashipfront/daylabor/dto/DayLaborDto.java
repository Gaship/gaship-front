package shop.gaship.gashipfront.daylabor.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Getter;

/**
 * 지역별 물량을 추가 수정 하기위한 클래스입니다.
 *
 * @author : 유호철
 * @since 1.0
 */

@Getter
public class DayLaborDto {
    @Min(1)
    @NotNull
    private Integer localNo;

    @Min(1)
    @NotNull
    private Integer maxLabr;
}

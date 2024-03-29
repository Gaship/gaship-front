package shop.gaship.gashipfront.productreview.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 상품평 조회 응답 dto입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
@Getter
@NoArgsConstructor
public class ProductReviewResponseDto {
    private Integer orderProductNo;
    private Integer writerNo;
    private String writerNickname;
    private String productName;
    private String title;
    private String content;
    private Integer starScore;
    private LocalDateTime registerDateTime;
    private LocalDateTime modifyDateTime;
    private List<String> filePaths;

    @Setter
    private Boolean isWriter;
}

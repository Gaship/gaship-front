package shop.gaship.gashipfront.util.dto;

import java.util.List;
import lombok.Getter;

/**
 * 목록 조회 반환 타입으로 사용되는 page 정보와 data 를 담은 Data Transfer Object.
 *
 * @param <T> the type parameter
 * @author : 최정우, 김세미
 * @since 1.0
 */
@Getter
public class PageResponse<T> {

    private List<T> content;

    private int totalPages;

    private int number;

    private boolean previous;

    private boolean next;
}

package shop.gaship.gashipfront.util;

/**
 * WebClientUtil에 사용될 쿼리 파라미터 객체입니다.
 *
 * @see WebClientUtil WebClientUtil
 * @author : 김민수
 * @since 1.0
 * @deprecated 팀원간의 투표를 통해서 사용하지않는 것으로 컨벤션을 정했음
 */
@Deprecated(forRemoval = true, since = "1.0")
public class QueryParam {
    String name;
    Object[] values;

    public QueryParam(String name, Object... values) {
        this.name = name;
        this.values = values;
    }

    public String getName() {
        return name;
    }

    public Object[] getValues() {
        return values;
    }
}

package shop.gaship.gashipfront.inquiry.inquiryenum;

/**
 * @author : 최겸준
 * @since 1.0
 */
public enum SuccessAttribute {
    CUSTOMER_INQUIRY_ADD_SUCCESS("고객문의 등록이 완료되었습니다."),
    PRODUCT_INQUIRY_ADD_SUCCESS("상품문의 등록이 완료되었습니다."),

    INQUIRY_ANSWER_ADD_SUCCESS("문의답변 등록이 완료되었습니다."),
    INQUIRY_ANSWER_MODIFY_SUCCESS("문의답변 수정이 완료되었습니다."),

    INQUIRY_DELETE_SUCCESS("문의 삭제가 완료되었습니다."),
    INQUIRY_ANSWER_DELETE_SUCCESS("문의답변 삭제가 완료되었습니다.");

    private final String value;

    SuccessAttribute(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

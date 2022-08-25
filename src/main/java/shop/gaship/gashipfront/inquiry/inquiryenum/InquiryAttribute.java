package shop.gaship.gashipfront.inquiry.inquiryenum;

/**
 * 문의에 대한 view에 넘길 데이터의 key와 value(message)를 저장해놓는 상수공간입니다.
 *
 * @author : 최겸준
 * @since 1.0
 */
public enum InquiryAttribute {
    VALUE_MESSAGE_CUSTOMER_INQUIRY_ADD_SUCCESS("고객문의 등록이 완료되었습니다."),
    VALUE_MESSAGE_PRODUCT_INQUIRY_ADD_SUCCESS("상품문의 등록이 완료되었습니다."),

    VALUE_MESSAGE_INQUIRY_ANSWER_ADD_SUCCESS("문의답변 등록이 완료되었습니다."),
    VALUE_MESSAGE_INQUIRY_ANSWER_MODIFY_SUCCESS("문의답변 수정이 완료되었습니다."),

    VALUE_MESSAGE_INQUIRY_DELETE_SUCCESS("문의 삭제가 완료되었습니다."),
    VALUE_MESSAGE_INQUIRY_ANSWER_DELETE_SUCCESS("문의답변 삭제가 완료되었습니다."),

    KEY_IS_SUCCESS("isSuccess"),
    KEY_SUCCESS_MESSAGE("successMessage"),

    KEY_PAGE_RESPONSE("pageResponse"),

    KEY_INQUIRY_DETAILS("inquiryDetails");

    private final String value;

    InquiryAttribute(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

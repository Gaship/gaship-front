package shop.gaship.gashipfront.inquiry.inquiryenum;

/**
 * 문의에 대한 view 이름들을 정의한 상수공간입니다.
 *
 * @author : 최겸준
 * @since 1.0
 */
public enum InquiryViewName {
    VIEW_NAME_CUSTOMER_INQUIRY_LIST("inquiries/customerInquiryList"),
    VIEW_NAME_PRODUCT_INQUIRY_LIST("inquiries/productInquiryList"),

    VIEW_NAME_CUSTOMER_INQUIRY_ADD_FORM("inquiries/customerInquiryAddForm"), // 사용자
    VIEW_NAME_PRODUCT_INQUIRY_ADD_FORM("inquiries/productInquiryAddForm"), // 사용자

    VIEW_NAME_CUSTOMER_INQUIRY_DETAILS("inquiries/customerInquiryDetails"), // 사용자, 관리자
    VIEW_NAME_PRODUCT_INQUIRY_DETAILS("inquiries/productInquiryDetails"); // 사용자, 관리자

    private String value;

    InquiryViewName(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

package shop.gaship.gashipfront.inquiry.inquiryenum;

/**
 * 문의에 대한 view 이름들을 정의한 상수공간입니다.
 *
 * @author : 최겸준
 * @since 1.0
 */
public enum InquiryViewName {
    VIEW_NAME_CUSTOMER_INQUIRY_LIST("inquiry/customerInquiryList"),
    VIEW_NAME_PRODUCT_INQUIRY_LIST("inquiry/productInquiryList"),
    VIEW_NAME_CUSTOMER_INQUIRY_LIST_ADMIN("inquiryadmin/customerInquiryListAdmin"),
    VIEW_NAME_PRODUCT_INQUIRY_LIST_ADMIN("inquiryadmin/productInquiryListAdmin"),

    VIEW_NAME_CUSTOMER_INQUIRY_ADD_FORM("inquiry/customerInquiryAddForm"), // 사용자
    VIEW_NAME_PRODUCT_INQUIRY_ADD_FORM("inquiry/productInquiryAddForm"), // 사용자

    VIEW_NAME_CUSTOMER_INQUIRY_DETAILS("inquiry/customerInquiryDetails"), // 사용자, 관리자
    VIEW_NAME_PRODUCT_INQUIRY_DETAILS("inquiry/productInquiryDetails"); // 사용자, 관리자

    private String value;

    InquiryViewName(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

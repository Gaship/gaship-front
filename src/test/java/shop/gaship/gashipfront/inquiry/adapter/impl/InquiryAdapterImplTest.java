package shop.gaship.gashipfront.inquiry.adapter.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.util.ReflectionTestUtils;
import shop.gaship.gashipfront.exceptions.RequestFailureThrow;
import shop.gaship.gashipfront.inquiry.adapter.InquiryAdapter;
import shop.gaship.gashipfront.inquiry.dto.request.InquiryAddRequestDto;
import shop.gaship.gashipfront.inquiry.dto.request.InquiryAnswerRequestDto;
import shop.gaship.gashipfront.inquiry.dto.response.InquiryDetailsResponseDto;
import shop.gaship.gashipfront.inquiry.dto.response.InquiryListResponseDto;
import shop.gaship.gashipfront.util.dto.PageResponse;

/**
 * adapter를 이용한 실제 통신테스트(커버리지를 올리기위한 테스트가 아닌 실제 api를 잘만들었는지 확인해보기 위한 테스트입니다.)
 * gateway, shopping-mall 서버가 모두 켜져있을때 정상작동합니다.
 *
 * @author : 최겸준
 * @since 1.0
 */
//@Disabled("gateway, shopping-mall 서버가 모두 켜져있을때 정상작동합니다. 서버가 모두 돌아갈때 해당 disabled를 주석처리하고 테스트해주세요.")
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class InquiryAdapterImplTest {
    @Autowired
    private InquiryAdapter inquiryAdapter;

    private static final Integer TEST_NO = 1;
    private InquiryAddRequestDto inquiryAddRequestDtoWhenCustomer;
    private InquiryAddRequestDto inquiryAddRequestDtoWhenProduct;

    private InquiryAnswerRequestDto inquiryAnswerRequestDtoWhenAdd;
    private InquiryAnswerRequestDto inquiryAnswerRequestDtoWhenModify;

    @BeforeEach
    void setUp() {
        inquiryAddRequestDtoWhenCustomer = new InquiryAddRequestDto();
        ReflectionTestUtils.setField(inquiryAddRequestDtoWhenCustomer, "memberNo", TEST_NO);
        ReflectionTestUtils.setField(inquiryAddRequestDtoWhenCustomer, "title", "고객문의 1번 제목");
        ReflectionTestUtils.setField(inquiryAddRequestDtoWhenCustomer, "inquiryContent",
            "고객문의 1번 내용");
        ReflectionTestUtils.setField(inquiryAddRequestDtoWhenCustomer, "isProduct", Boolean.FALSE);

        inquiryAddRequestDtoWhenProduct = new InquiryAddRequestDto();
        new InquiryAddRequestDto();
        ReflectionTestUtils.setField(inquiryAddRequestDtoWhenProduct, "memberNo", TEST_NO);
        ReflectionTestUtils.setField(inquiryAddRequestDtoWhenProduct, "productNo", TEST_NO);
        ReflectionTestUtils.setField(inquiryAddRequestDtoWhenProduct, "title", "상품문의 1번 제목");
        ReflectionTestUtils.setField(inquiryAddRequestDtoWhenProduct, "inquiryContent",
            "상품문의 1번 내용");
        ReflectionTestUtils.setField(inquiryAddRequestDtoWhenProduct, "isProduct", Boolean.TRUE);

        inquiryAnswerRequestDtoWhenAdd = new InquiryAnswerRequestDto();
        ReflectionTestUtils.setField(inquiryAnswerRequestDtoWhenAdd, "inquiryNo", TEST_NO);
        ReflectionTestUtils.setField(inquiryAnswerRequestDtoWhenAdd, "employeeNo", TEST_NO);
        ReflectionTestUtils.setField(inquiryAnswerRequestDtoWhenAdd, "answerContent",
            "문의 1번 내용에 대한 답변 추가");

        inquiryAnswerRequestDtoWhenModify = new InquiryAnswerRequestDto();
        ReflectionTestUtils.setField(inquiryAnswerRequestDtoWhenModify, "inquiryNo",
            TEST_NO);
        ReflectionTestUtils.setField(inquiryAnswerRequestDtoWhenModify, "employeeNo",
            TEST_NO);
        ReflectionTestUtils.setField(inquiryAnswerRequestDtoWhenModify, "answerContent",
            "문의 1번 내용에 대한 답변 수정");
    }

    @Order(1)
    @DisplayName("문의 답변 추가가 잘 이루어진다.")
    @Test
    void inquiryAnswerAdd() {
        try {
            inquiryAdapter.inquiryAnswerDelete(TEST_NO);
        } catch (RequestFailureThrow e) {
            if (!Objects.equals(e.getMessage(), "등록되지 않은 답변입니다. 답변을 먼저 등록해주세요.")) {
                throw e;
            }
        }

        assertThatNoException().isThrownBy(() -> inquiryAdapter.inquiryAnswerAdd(inquiryAnswerRequestDtoWhenAdd));
    }

    @Order(2)
    @DisplayName("문의 답변 추가가 되어있는데 다시 추가를하면 예외가 발생한다.")
    @Test
    void inquiryAnswerAdd_fail() {
        assertThatThrownBy(() -> inquiryAdapter.inquiryAnswerAdd(inquiryAnswerRequestDtoWhenAdd))
            .isInstanceOf(RequestFailureThrow.class)
            .hasMessageContaining("해당 문의에 답변이 이미 등록되어 있습니다.");
    }

    @Order(3)
    @DisplayName("문의 답변 수정이 잘 이루어진다.")
    @Test
    void inquiryAnswerModify() {
        assertThatNoException().isThrownBy(() -> inquiryAdapter.inquiryAnswerModify(inquiryAnswerRequestDtoWhenModify));
    }

    @Order(4)
    @DisplayName("문의 답변이 db에서 잘 삭제된다. 삭제될시에 employee_no, answer_content, answer_register_datetime, answer_modify_datetime이 null이 된다.")
    @Test
    void inquiryAnswerDelete() {
        assertThatNoException().isThrownBy(() -> inquiryAdapter.inquiryAnswerDelete(
            TEST_NO));
    }

    @Order(5)
    @DisplayName("문의 답변 추가가 안되어있는데 수정을 하면 예외가 발생한다.")
    @Test
    void inquiryAnswerModify_fail() {
        assertThatThrownBy(() -> inquiryAdapter.inquiryAnswerModify(inquiryAnswerRequestDtoWhenModify))
            .isInstanceOf(RequestFailureThrow.class)
            .hasMessageContaining("등록되지 않은 답변입니다. 답변을 먼저 등록해주세요.");
    }

    @Order(6)
    @DisplayName("문의 답변 추가가 안되어있는데 수정을 하면 예외가 발생한다.")
    @Test
    void inquiryAnswerDelete_fail() {
        assertThatThrownBy(() -> inquiryAdapter.inquiryAnswerDelete(TEST_NO))
            .isInstanceOf(RequestFailureThrow.class)
            .hasMessageContaining("등록되지 않은 답변입니다. 답변을 먼저 등록해주세요.");
    }

    @DisplayName("문의 추가가 잘 이루어진다. 잘이루어 진것은 db에서 확인한다.")
    @Test
    void inquiryAdd() {
        assertThatNoException().isThrownBy(() -> inquiryAdapter.inquiryAdd(inquiryAddRequestDtoWhenCustomer));
    }

    @DisplayName("문의 상세조회가 잘 이루어진다.")
    @Test
    void inquiryDetails() {
        InquiryDetailsResponseDto inquiryDetailsResponseDto = inquiryAdapter.inquiryDetails(
            TEST_NO);
        assertThat(inquiryDetailsResponseDto.getInquiryNo())
            .isEqualTo(TEST_NO);
    }

    @Disabled("(주의) 해당 설정은 직접 db에 존재하는 번호를 확인한뒤에 해야한다. 안그러면 예외가 발생한다. 그래서 주의가 필요하기때문에 disabled해놓았다.")
    @DisplayName("문의가 존재할때 잘 삭제된다.")
    @Test
    void inquiryDelete() {
        assertThatNoException().isThrownBy(() -> inquiryAdapter.inquiryDelete(7));
    }

    @Disabled("(주의) 해당 설정은 직접 db에 존재하는 번호를 확인한뒤에 해야한다. 안그러면 예외가 발생한다. 그래서 주의가 필요하기때문에 disabled해놓았다.")
    @DisplayName("존재하지 않는 문의번호로 문의 삭제요청 할 시에 예외가 발생한다.")
    @Test
    void inquiryDelete_fail() {
        assertThatThrownBy(() -> inquiryAdapter.inquiryDelete(7))
            .isInstanceOf(RequestFailureThrow.class)
            .hasMessageContaining("문의를 찾을 수 없습니다.");
    }

    @DisplayName("고객문의 목록 조회가 예외없이 잘 요청된다.")
    @Test
    void customerInquiryList() {
        PageResponse<InquiryListResponseDto> pageResponse = inquiryAdapter.customerInquiryList(PageRequest.of(0, 5));
        List<InquiryListResponseDto> content = pageResponse.getContent();

        assertThat(pageResponse.getPage())
            .isZero();

        InquiryListResponseDto actual = content.get(0);
        assertThat(actual)
            .isNotNull();
    }

    @DisplayName("상품문의 목록조회가 예외없이 잘 요청된다.")
    @Test
    void productInquiryList() {

        PageResponse<InquiryListResponseDto> pageResponse = inquiryAdapter.productInquiryList(PageRequest.of(0, 5));
        List<InquiryListResponseDto> content = pageResponse.getContent();

        assertThat(pageResponse.getPage())
            .isZero();

        InquiryListResponseDto actual = content.get(0); // 이러면 항상 1번이 나온다(등록되어있을시)
        assertThat(actual)
            .isNotNull();
    }

    @DisplayName("답변대기중인 고객문의 목록을 불러온다.")
    @Test
    void customerInquiryStatusHoldList() {
        try {
            inquiryAdapter.inquiryAnswerDelete(TEST_NO);
        } catch (RequestFailureThrow e) {
            if (!Objects.equals(e.getMessage(), "등록되지 않은 답변입니다. 답변을 먼저 등록해주세요.")) {
                throw e;
            }
        }

        PageResponse<InquiryListResponseDto> pageResponse = inquiryAdapter.customerInquiryStatusHoldList(PageRequest.of(0, 5));
        List<InquiryListResponseDto> content = pageResponse.getContent();

        assertThat(pageResponse.getPage())
            .isZero();

        InquiryListResponseDto actual = content.get(0); // 이러면 항상 1번이 나온다(등록되어있을시)
        assertThat(actual)
            .isNotNull();

        content.stream().forEach(c -> {
            assertThat(c.getProcessStatus())
                .isEqualTo("답변대기");
        });
    }

    @DisplayName("답변완료인 고객문의 목록을 불러온다.")
    @Test
    void customerInquiryStatusCompleteList() {
        try {
            inquiryAdapter.inquiryAnswerAdd(inquiryAnswerRequestDtoWhenAdd);
        } catch (RequestFailureThrow e) {
            if (!Objects.equals(e.getMessage(), "해당 문의에 답변이 이미 등록되어 있습니다.")) {
                throw e;
            }
        }

        PageResponse<InquiryListResponseDto> pageResponse = inquiryAdapter.customerInquiryStatusCompleteList(PageRequest.of(0, 5));
        List<InquiryListResponseDto> content = pageResponse.getContent();

        assertThat(pageResponse.getPage())
            .isZero();

        InquiryListResponseDto actual = content.get(0); // 이러면 항상 1번이 나온다(등록되어있을시)
        assertThat(actual)
            .isNotNull();

        content.stream().forEach(c -> {
            assertThat(c.getProcessStatus())
                .isEqualTo("답변완료");
        });
    }

    @DisplayName("답변대기인 상품문의 목록을 가져온다.")
    @Test
    void productInquiryStatusHoldList() {
        inquiryAdapter.inquiryAdd(inquiryAddRequestDtoWhenProduct);

        PageResponse<InquiryListResponseDto> pageResponse = inquiryAdapter.productInquiryStatusHoldList(PageRequest.of(0, 5));
        List<InquiryListResponseDto> content = pageResponse.getContent();

        assertThat(pageResponse.getPage())
            .isZero();

        InquiryListResponseDto actual = content.get(0); // 이러면 항상 1번이 나온다(등록되어있을시)
        assertThat(actual)
            .isNotNull();

        content.stream().forEach(c -> {
            assertThat(c.getProcessStatus())
                .isEqualTo("답변대기");
        });

    }

    @Disabled("db에 답변완료된 상태의 번호를 확인하고 넣어야한다. 그래서 주의가 필요하기때문에 disabled해놓았다.")
    @DisplayName("답변완료인 상품문의 목록을 가져온다")
    @Test
    void productInquiryStatusCompleteList() {
        try {
            ReflectionTestUtils.setField(inquiryAnswerRequestDtoWhenAdd, "inquiryNo", 3);
            inquiryAdapter.inquiryAnswerAdd(inquiryAnswerRequestDtoWhenAdd);
        } catch (RequestFailureThrow e) {
            if (!Objects.equals(e.getMessage(), "해당 문의에 답변이 이미 등록되어 있습니다.")) {
                throw e;
            }
        }

        PageResponse<InquiryListResponseDto> pageResponse = inquiryAdapter.productInquiryStatusCompleteList(PageRequest.of(0, 5));
        List<InquiryListResponseDto> content = pageResponse.getContent();

        assertThat(pageResponse.getPage())
            .isZero();

        content.stream().forEach(c -> {
            assertThat(c.getProcessStatus())
                .isEqualTo("답변완료");
        });
    }

    @DisplayName("특정 고객에 대한 고객문의를 조회한다.")
    @Test
    void customerInquiryMemberList() {
        PageResponse<InquiryListResponseDto> pageResponse = inquiryAdapter.customerInquiryMemberList(PageRequest.of(0, 5), TEST_NO);
        List<InquiryListResponseDto> content = pageResponse.getContent();

        InquiryDetailsResponseDto inquiryDetails = inquiryAdapter.inquiryDetails(TEST_NO);

        assertThat(pageResponse.getPage())
            .isZero();

        content.stream().forEach(c -> {
            assertThat(c.getMemberNickname())
                .isEqualTo(inquiryDetails.getMemberNickname());
        });
    }

    @DisplayName("특정 고객에 대한 상품문의를 조회한다.")
    @Test
    void productInquiryMemberList() {
        inquiryAdapter.inquiryAdd(inquiryAddRequestDtoWhenProduct);
        inquiryAdapter.inquiryAdd(inquiryAddRequestDtoWhenProduct);

        PageResponse<InquiryListResponseDto> pageResponse = inquiryAdapter.productInquiryMemberList(PageRequest.of(0, 5), TEST_NO);
        List<InquiryListResponseDto> content = pageResponse.getContent();

        InquiryDetailsResponseDto inquiryDetails = inquiryAdapter.inquiryDetails(content.get(0).getInquiryNo());

        assertThat(pageResponse.getPage())
            .isZero();

        content.stream().forEach(c -> {
            assertThat(c.getMemberNickname())
                .isEqualTo(inquiryDetails.getMemberNickname());
        });
    }

    @DisplayName("특정 상품에 대한 상품문의를 조회한다.")
    @Test
    void productInquiryProductList() {
        inquiryAdapter.inquiryAdd(inquiryAddRequestDtoWhenProduct);
        inquiryAdapter.inquiryAdd(inquiryAddRequestDtoWhenProduct);

        PageResponse<InquiryListResponseDto> pageResponse = inquiryAdapter.productInquiryProductList(PageRequest.of(0, 5), TEST_NO);
        List<InquiryListResponseDto> content = pageResponse.getContent();

        InquiryDetailsResponseDto inquiryDetails = inquiryAdapter.inquiryDetails(content.get(0).getInquiryNo());

        assertThat(pageResponse.getPage())
            .isZero();
        
        content.stream().forEach(c -> {
            Integer inquiryNo = c.getInquiryNo();
            InquiryDetailsResponseDto inquiry = inquiryAdapter.inquiryDetails(inquiryNo);
            assertThat(inquiry.getProductName())
                .isEqualTo(inquiryDetails.getProductName());
        });
    }
}
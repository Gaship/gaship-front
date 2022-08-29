package shop.gaship.gashipfront.inquiry.service.common.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.gaship.gashipfront.inquiry.adapter.InquiryAdapter;
import shop.gaship.gashipfront.inquiry.dto.request.InquiryAddRequestDto;
import shop.gaship.gashipfront.inquiry.dto.request.InquiryAnswerRequestDto;
import shop.gaship.gashipfront.inquiry.dto.response.InquiryDetailsResponseDto;
import shop.gaship.gashipfront.inquiry.service.common.CommonInquiryService;

/**
 * CommonInquiryService를 구현한 클래스입니다.
 *
 * @author 최겸준
 * @see shop.gaship.gashipfront.inquiry.service.common.CommonInquiryService
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class CommonInquiryServiceImpl implements CommonInquiryService {

    private final InquiryAdapter inquiryAdapter;

    /**
     * {@inheritDoc}
     */
    @Override
    public void addInquiry(InquiryAddRequestDto inquiryAddRequestDto) {
        inquiryAdapter.inquiryAdd(inquiryAddRequestDto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addInquiryAnswer(InquiryAnswerRequestDto inquiryAnswerAddRequestDto) {
        inquiryAdapter.inquiryAnswerAdd(inquiryAnswerAddRequestDto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void modifyInquiryAnswer(InquiryAnswerRequestDto inquiryAnswerModifyRequestDto) {
        inquiryAdapter.inquiryAnswerModify(inquiryAnswerModifyRequestDto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteInquiry(Integer inquiryNo, Integer memberNo) {
        inquiryAdapter.inquiryDelete(inquiryNo, memberNo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteInquiryManager(Integer inquiryNo) {
        inquiryAdapter.inquiryDeleteManager(inquiryNo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteInquiryAnswer(Integer inquiryNo) {
        inquiryAdapter.inquiryAnswerDelete(inquiryNo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InquiryDetailsResponseDto findInquiry(Integer inquiryNo) {
        return inquiryAdapter.inquiryDetails(inquiryNo);
    }
}

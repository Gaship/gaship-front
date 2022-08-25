package shop.gaship.gashipfront.renewalperiod.adapter;

import shop.gaship.gashipfront.renewalperiod.dto.response.RenewalPeriodResponseDto;

/**
 *
 *
 * @author : 김세미
 * @since 1.0
 */
public interface RenewalPeriodAdapter {
    void modifyRenewalPeriod(Integer value);
    RenewalPeriodResponseDto findRenewalPeriod();
}

package shop.gaship.gashipfront.renewalperiod.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.gaship.gashipfront.renewalperiod.adapter.RenewalPeriodAdapter;
import shop.gaship.gashipfront.renewalperiod.dto.response.RenewalPeriodResponseDto;
import shop.gaship.gashipfront.renewalperiod.service.RenewalPeriodService;

/**
 *
 *
 * @author : 김세미
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class RenewalPeriodServiceImpl implements RenewalPeriodService {
    private final RenewalPeriodAdapter renewalPeriodAdapter;

    @Override
    public RenewalPeriodResponseDto findRenewalPeriod() {
        return renewalPeriodAdapter.findRenewalPeriod();
    }
}

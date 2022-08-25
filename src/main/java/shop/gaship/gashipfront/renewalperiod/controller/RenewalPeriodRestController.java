package shop.gaship.gashipfront.renewalperiod.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.gaship.gashipfront.renewalperiod.dto.response.RenewalPeriodResponseDto;
import shop.gaship.gashipfront.renewalperiod.service.RenewalPeriodService;

/**
 *
 *
 * @author : 김세미
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/renewal-period")
public class RenewalPeriodRestController {
    private final RenewalPeriodService renewalPeriodService;

    @GetMapping
    public RenewalPeriodResponseDto renewalPeriodDetails() {
        return renewalPeriodService.findRenewalPeriod();
    }
}

package shop.gaship.gashipfront.totalsale.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.gaship.gashipfront.totalsale.adater.TotalSaleAdapter;
import shop.gaship.gashipfront.totalsale.dto.request.TotalSaleRequestDto;
import shop.gaship.gashipfront.totalsale.dto.response.TotalSaleResponseDto;
import shop.gaship.gashipfront.totalsale.service.TotalSaleService;

/**
 * @author : 조재철
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class TotalSaleServiceImpl implements TotalSaleService {

    private final TotalSaleAdapter totalSaleAdapter;

    @Override
    public List<TotalSaleResponseDto> findTotalSaleList(
        TotalSaleRequestDto totalSaleRequestDto) {
        return totalSaleAdapter.findTotalSaleList(totalSaleRequestDto);
    }

}

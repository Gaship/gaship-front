package shop.gaship.gashipfront.totalsale.adater;

import java.util.List;
import shop.gaship.gashipfront.totalsale.dto.request.TotalSaleRequestDto;
import shop.gaship.gashipfront.totalsale.dto.response.TotalSaleResponseDto;

/**
 * @author : 조재철
 * @since 1.0
 */
public interface TotalSaleAdapter {

    List<TotalSaleResponseDto> findTotalSaleList(
        TotalSaleRequestDto totalSaleRequestDto);
}

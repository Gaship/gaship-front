package shop.gaship.gashipfront.totalsale.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.gaship.gashipfront.totalsale.dto.request.TotalSaleRequestDto;
import shop.gaship.gashipfront.totalsale.dto.response.TotalSaleResponseDto;
import shop.gaship.gashipfront.totalsale.service.TotalSaleService;

/**
 * @author : 조재철
 * @since 1.0
 */
@RestController
@RequestMapping("/admin/total-sale")
@RequiredArgsConstructor
public class TotalSaleRestController {
    private final TotalSaleService totalSaleService;

    @PostMapping
    public List<TotalSaleResponseDto> totalSaleDate(@RequestBody TotalSaleRequestDto totalSaleRequestDto) {

        return totalSaleService.findTotalSaleList(totalSaleRequestDto);
    }
}

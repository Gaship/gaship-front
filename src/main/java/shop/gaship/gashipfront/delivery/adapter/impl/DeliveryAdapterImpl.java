package shop.gaship.gashipfront.delivery.adapter.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import shop.gaship.gashipfront.delivery.adapter.DeliveryAdapter;
import shop.gaship.gashipfront.delivery.dto.response.DeliveryInfoStatusResponseDto;
import shop.gaship.gashipfront.delivery.dto.response.DeliveryLocationResponseDto;
import shop.gaship.gashipfront.delivery.dto.response.TrackingNoResponseDto;
import shop.gaship.gashipfront.security.common.exception.NullResponseBodyException;
import shop.gaship.gashipfront.util.ExceptionUtil;

/**
 * @author : 조재철
 * @since 1.0
 */
@RequiredArgsConstructor
@Component
@ConfigurationProperties(prefix = "eggplant-server")
public class DeliveryAdapterImpl implements DeliveryAdapter {

    private final WebClient webClient;
    public static final String DELIVERY_PREFIX_URI = "/api/delivery";

    private String deliveryUrl;

    public void setDeliveryUrl(String deliveryUrl) {
        this.deliveryUrl = deliveryUrl;
    }

    @Override
    public void addTrackingNo(TrackingNoResponseDto trackingNoResponseDto) {
        webClient.post().
                 uri(DELIVERY_PREFIX_URI + "/add-tracking-no")
                 .bodyValue(trackingNoResponseDto)
                 .retrieve()
                 .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
                 .toEntity(Void.class)
                 .block();

    }

    @Override
    public void changeDeliveryStatus(DeliveryInfoStatusResponseDto deliveryInfoStatusResponseDto) {
        webClient.patch().
                 uri(DELIVERY_PREFIX_URI + "/change-delivery-info")
                 .bodyValue(deliveryInfoStatusResponseDto)
                 .retrieve()
                 .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
                 .toEntity(Void.class)
                 .block();
    }

    @Override
    public List<DeliveryLocationResponseDto> findDeliveryLocationInfo(String trackingNo) {
        WebClient deliveryWebClient = WebClient.builder()
                                               .baseUrl(deliveryUrl)
                                               .build();

        return deliveryWebClient.get()
                                .uri(uriBuilder -> uriBuilder.path("/eggplant-delivery/tracking-no")
                                                             .queryParam("trackingNo", trackingNo)
                                                             .build())
                                .retrieve()
                                .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
                                .bodyToMono(new ParameterizedTypeReference<List<DeliveryLocationResponseDto>>() {
                                })
                                .blockOptional()
                                .orElseThrow(NullResponseBodyException::new);

    }

}

package com.foodcourt.court.infrastructure.out.rest.client;

import com.foodcourt.court.domain.model.TrackingOrder;
import com.foodcourt.court.infrastructure.configuration.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name="tracking-mngr", url = "${external.api.tracking-mngr.url}", configuration = FeignClientConfig.class)
public interface ITrackingRestClient {

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    void createTrackingOrder(@RequestBody TrackingOrder trackingOrder);

}

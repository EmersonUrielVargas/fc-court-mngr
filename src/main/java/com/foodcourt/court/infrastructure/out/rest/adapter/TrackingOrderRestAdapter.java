package com.foodcourt.court.infrastructure.out.rest.adapter;

import com.foodcourt.court.domain.model.TrackingOrder;
import com.foodcourt.court.domain.spi.ITrackingOrderPersistencePort;
import com.foodcourt.court.infrastructure.out.rest.client.ITrackingRestClient;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TrackingOrderRestAdapter implements ITrackingOrderPersistencePort {

    private final ITrackingRestClient trackingRestClient;

    @Override
    public void createTrackingOrder(TrackingOrder trackingOrder) {
        trackingRestClient.createTrackingOrder(trackingOrder);
    }
}

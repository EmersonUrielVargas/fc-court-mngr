package com.foodcourt.court.domain.spi;

import com.foodcourt.court.domain.model.TrackingOrder;

public interface ITrackingOrderPersistencePort {
    void createTrackingOrder(TrackingOrder trackingOrder);
}

package com.foodcourt.court.domain.spi;

public interface INotificationPort {
    void sendTextMessage(String message, String numberPhone);
}

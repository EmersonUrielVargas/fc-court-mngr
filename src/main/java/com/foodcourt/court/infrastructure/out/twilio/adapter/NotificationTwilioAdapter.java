package com.foodcourt.court.infrastructure.out.twilio.adapter;

import com.foodcourt.court.domain.spi.INotificationPort;
import com.foodcourt.court.infrastructure.out.twilio.service.NotificationService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NotificationTwilioAdapter implements INotificationPort {

    private final NotificationService notificationService;

    @Override
    public void sendTextMessage(String message, String numberPhone) {
        notificationService.sendSms(message, numberPhone);
    }
}
